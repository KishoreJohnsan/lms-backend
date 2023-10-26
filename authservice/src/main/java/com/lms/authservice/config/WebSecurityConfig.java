package com.lms.authservice.config;

import com.lms.authservice.security.JwtAuthEntryPoint;
import com.lms.authservice.security.JwtAuthFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.List;

@ComponentScan(basePackages = {"com.lms.authservice"})
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthFilter authFilter;
    private final JwtAuthEntryPoint authEntryPoint;
    private final AuthenticationProvider authenticationProvider;

    private static final String COURSE_URL_PATH = "/**/lms/courses/**/**";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/**/authenticate", "/**/register/**", "/**/v3/api-docs", "**/swagger-ui/**").permitAll()
                .requestMatchers(HttpMethod.GET,COURSE_URL_PATH).permitAll()
                .requestMatchers(HttpMethod.DELETE,COURSE_URL_PATH).hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST,COURSE_URL_PATH).hasRole("ADMIN")
                .anyRequest().authenticated().and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().authenticationEntryPoint(authEntryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.cors().configurationSource(request-> {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(List.of("*"));
            configuration.setAllowedMethods(Arrays.asList("GET","POST","DELETE","PUT"));
            configuration.setAllowedHeaders(List.of("*"));
            return configuration;
        });

        return http.build();

    }

}
