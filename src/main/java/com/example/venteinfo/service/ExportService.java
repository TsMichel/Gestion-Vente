package com.example.venteinfo.service;

import com.example.venteinfo.model.Client;
import com.example.venteinfo.model.Produit;
import com.example.venteinfo.model.Vente;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress; // Importation ajoutée
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExportService {

    public ByteArrayInputStream exportToExcel(List<Client> clients, List<Produit> produits, List<Vente> ventes) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            if (clients != null && !clients.isEmpty()) {
                Sheet sheet = workbook.createSheet("Clients");
                Row header = sheet.createRow(0);
                header.createCell(0).setCellValue("ID");
                header.createCell(1).setCellValue("Nom");
                header.createCell(2).setCellValue("Email");
                header.createCell(3).setCellValue("Téléphone");

                int rowIdx = 1;
                for (Client client : clients) {
                    Row row = sheet.createRow(rowIdx++);
                    row.createCell(0).setCellValue(client.getId());
                    row.createCell(1).setCellValue(client.getNom());
                    row.createCell(2).setCellValue(client.getEmail());
                    row.createCell(3).setCellValue(client.getTelephone());
                }
            }

            if (produits != null && !produits.isEmpty()) {
                Sheet sheet = workbook.createSheet("Produits");
                Row header = sheet.createRow(0);
                header.createCell(0).setCellValue("ID");
                header.createCell(1).setCellValue("Nom");
                header.createCell(2).setCellValue("Description");
                header.createCell(3).setCellValue("Prix");
                header.createCell(4).setCellValue("Stock");

                int rowIdx = 1;
                for (Produit produit : produits) {
                    Row row = sheet.createRow(rowIdx++);
                    row.createCell(0).setCellValue(produit.getId());
                    row.createCell(1).setCellValue(produit.getNom());
                    row.createCell(2).setCellValue(produit.getDescription());
                    row.createCell(3).setCellValue(produit.getPrix());
                    row.createCell(4).setCellValue(produit.getStock());
                }
            }

            if (ventes != null && !ventes.isEmpty()) {
                Sheet sheet = workbook.createSheet("Ventes");
                Row header = sheet.createRow(0);
                header.createCell(0).setCellValue("ID");
                header.createCell(1).setCellValue("Client");
                header.createCell(2).setCellValue("Produit");
                header.createCell(3).setCellValue("Quantité");
                header.createCell(4).setCellValue("Montant Total");
                header.createCell(5).setCellValue("Date Vente");

                int rowIdx = 1;
                for (Vente vente : ventes) {
                    Row row = sheet.createRow(rowIdx++);
                    row.createCell(0).setCellValue(vente.getId());
                    row.createCell(1).setCellValue(vente.getClient().getNom());
                    row.createCell(2).setCellValue(vente.getProduit().getNom());
                    row.createCell(3).setCellValue(vente.getQuantite());
                    row.createCell(4).setCellValue(vente.getMontantTotal());
                    row.createCell(5).setCellValue(vente.getDateVente().toString());
                }
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'exportation en Excel", e);
        }
    }

    public ByteArrayInputStream generateFacture(Vente vente) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Facture");
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // En-tête de la facture
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Facture");
            headerRow.getCell(0).setCellStyle(headerStyle);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));

            Row infoRow = sheet.createRow(1);
            infoRow.createCell(0).setCellValue("Numéro de Facture: FACT-" + vente.getId());
            infoRow.createCell(2).setCellValue("Date: " + LocalDateTime.now()); // Supprimé toString()

            Row clientRow = sheet.createRow(2);
            clientRow.createCell(0).setCellValue("Client: " + vente.getClient().getNom());
            clientRow.createCell(2).setCellValue("Email: " + vente.getClient().getEmail());

            // En-tête du tableau
            Row tableHeader = sheet.createRow(4);
            String[] headers = {"Produit", "Quantité", "Prix Unitaire", "Montant Total"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = tableHeader.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // Détails de la vente
            Row dataRow = sheet.createRow(5);
            Produit produit = vente.getProduit();
            dataRow.createCell(0).setCellValue(produit.getNom());
            dataRow.createCell(1).setCellValue(vente.getQuantite());
            dataRow.createCell(2).setCellValue(produit.getPrix());
            dataRow.createCell(3).setCellValue(vente.getMontantTotal());

            // Total
            Row totalRow = sheet.createRow(6);
            totalRow.createCell(2).setCellValue("Total:");
            totalRow.createCell(3).setCellValue(vente.getMontantTotal());

            // Ajuster la largeur des colonnes
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la génération de la facture", e);
        }
    }
}