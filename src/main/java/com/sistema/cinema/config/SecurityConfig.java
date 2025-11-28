package com.sistema.cinema.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // permite acesso anônimo às páginas de login e recursos estáticos
                .requestMatchers(
                    "/login",
                    "/register/**",
                    "/css/**",
                    "/js/**",
                    "/images/**",
                    "/static/**"
                ).permitAll()

                // endpoints que só ADMIN pode acessar (criação/edição/exclusão)
                .requestMatchers(
                    "/admin/**",
                    "/roles/**",
                    "/usuario/**",
                    "/role/**",

                    "/sala/form",
                    "/sala/save",
                    "/sala/edit/**",
                    "/sala/delete/**",

                    "/equipamento/form",
                    "/equipamento/save",
                    "/equipamento/edit/**",
                    "/equipamento/delete/**",

                    "/sessao/form",
                    "/sessao/save",
                    "/sessao/edit/**",
                    "/sessao/delete/**"
                ).hasRole("ADMIN")

                // todas as outras solicitações exigem autenticação (listas e visualizações ficam aqui)
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login") // página personalizada de login
                .defaultSuccessUrl("/pagina-inicial", true) // redireciona após login
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}