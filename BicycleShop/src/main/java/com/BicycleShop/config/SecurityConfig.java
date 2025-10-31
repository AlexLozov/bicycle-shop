package com.BicycleShop.config;

import com.BicycleShop.security.filter.JwtRequestFilter;
import com.BicycleShop.security.handler.AccessRestrictionHandler;
import com.BicycleShop.service.UserService;
import com.BicycleShop.service.model.IamServiceUserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AccessRestrictionHandler accessRestrictionHandler;
    private final JwtRequestFilter jwtRequestFilter;

    private static final String POST = "POST";
    private static final String GET = "GET";
    private static final String PUT = "PUT";
    private static final String DELETE = "DELETE";

    private static final AntPathRequestMatcher[] NOT_SECURED_URLS = new AntPathRequestMatcher[]{
            new AntPathRequestMatcher("/auth/login", POST),
            new AntPathRequestMatcher("/auth/register", POST),
            new AntPathRequestMatcher("/auth/refresh/token", GET),

            new AntPathRequestMatcher("/v3/api-docs/**"),
            new AntPathRequestMatcher("/swagger-ui/**"),
            new AntPathRequestMatcher("/swagger-ui.html"),
            new AntPathRequestMatcher("//webjars/**")
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(NOT_SECURED_URLS).permitAll()

                        .requestMatchers(get("/users/all")).hasAnyAuthority(adminAccessSecurityRoles())
                        .requestMatchers(get("/users/full/{id}")).hasAnyAuthority(adminAccessSecurityRoles())
                        .requestMatchers(post("/users/create")).hasAnyAuthority(adminAccessSecurityRoles())
                        .requestMatchers(delete("/users/{id}")).hasAnyAuthority(adminAccessSecurityRoles())

                        .requestMatchers(get("/bicycle/all")).hasAnyAuthority(adminAccessSecurityRoles())
                        .requestMatchers(post("/bicycle/create")).hasAnyAuthority(adminAccessSecurityRoles())
                        .requestMatchers(put("/bicycle/{id}")).hasAnyAuthority(adminAccessSecurityRoles())
                        .requestMatchers(delete("/bicycle/{id}")).hasAnyAuthority(adminAccessSecurityRoles())



                        .anyRequest().authenticated()
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                        .accessDeniedHandler(accessRestrictionHandler)

                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(UserService userService) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userService);
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    private String[] adminAccessSecurityRoles(){
        return new String[]{
                IamServiceUserRole.ADMIN.name(),
                IamServiceUserRole.SUPER_ADMIN.name()
        };
    }

    private static AntPathRequestMatcher get(String pattern){
        return new AntPathRequestMatcher(pattern, GET);
    }

    private static AntPathRequestMatcher post(String pattern){
        return new AntPathRequestMatcher(pattern, POST);
    }

    private static AntPathRequestMatcher put(String pattern){
        return new AntPathRequestMatcher(pattern, PUT);
    }

    private static AntPathRequestMatcher delete(String pattern){
        return new AntPathRequestMatcher(pattern, DELETE);
    }
}
