package com.dmdev.spring.config;

import com.dmdev.spring.database.entity.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfiguration {


    @Bean
//    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        (urlConfig) -> urlConfig
                                .requestMatchers(new AntPathRequestMatcher("/login"), new AntPathRequestMatcher("/users/registration")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/users/{\\d+}/delete")).hasAuthority(Role.ADMIN.getAuthority())
                                .requestMatchers(new AntPathRequestMatcher("/admin/**")).hasAuthority(Role.ADMIN.getAuthority())
                                .anyRequest().authenticated()

        );
        http
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login"))
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/users"));


//        http.httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
