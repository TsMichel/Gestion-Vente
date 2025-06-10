package com.example.venteinfo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/").permitAll()         // /home accessible sans login
                        .requestMatchers("/", "/", "/").authenticated() // Pages protégées
                        .anyRequest().authenticated()                 // Tout le reste nécessite une authentification
                )
                .formLogin(form -> form
                        .permitAll()                                  // Page de login par défaut accessible à tous
                        .failureHandler((request, response, exception) -> {
                            // Rediriger vers /home en cas d'échec d'authentification
                            response.sendRedirect("/home");
                        })
                        .defaultSuccessUrl("/home", true)         // Redirection après succès
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/home")
                        .permitAll()
                );

        return http.build();
    }
}


//
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
////EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .formLogin(form -> form
//                        .loginPage("/login")
//                        .permitAll()
//                )
//                .oauth2Login(oauth2 -> oauth2
//                        .loginPage("/login")
//                )
//                .logout(logout -> logout
//                        .permitAll()
//                );
//        return http.build();
//    }
//}