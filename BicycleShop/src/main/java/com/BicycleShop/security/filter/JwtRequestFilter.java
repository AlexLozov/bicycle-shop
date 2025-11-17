package com.BicycleShop.security.filter;

import com.BicycleShop.model.constants.ApiErrorMessage;
import com.BicycleShop.security.JwtTokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String COOKIE_NAME = "AuthToken";

    private final JwtTokenProvider jwtTokenProvider;


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        String path = request.getServletPath();

        // эндпоинты, которые разрешены в SecurityConfig
        return path.startsWith("/auth/login")
                || path.startsWith("/auth/register")
                || path.startsWith("/auth/refresh/token")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/swagger-ui.html")
                || path.startsWith("/webjars")
                || path.startsWith("/bicycle/all");   // ← ВАЖНО
    }

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain
    ) throws ServletException, IOException {

        String jwt = resolveToken(request);

        if (jwt != null) {
            try {
                if (!jwtTokenProvider.validateToken(jwt)) {
                    throw new ExpiredJwtException(null, null, ApiErrorMessage.TOKEN_EXPIRED.getMessage());
                }

                String email = jwtTokenProvider.getUsername(jwt);
                String userId = jwtTokenProvider.getUserId(jwt);

                if (email != null && userId != null &&
                        SecurityContextHolder.getContext().getAuthentication() == null) {

                    List<SimpleGrantedAuthority> authorities = jwtTokenProvider.getRoles(jwt).stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(email, null, authorities);

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }

            } catch (ExpiredJwtException e) {
                sendError(response, HttpStatus.UNAUTHORIZED, ApiErrorMessage.TOKEN_EXPIRED.getMessage());
                return;
            } catch (SignatureException | MalformedJwtException e) {
                sendError(response, HttpStatus.UNAUTHORIZED, ApiErrorMessage.INVALID_TOKEN_SIGNATURE.getMessage());
                return;
            } catch (Exception e) {
                log.error(ApiErrorMessage.ERROR_DURING_JWT_PROCESSING.getMessage(), e);
                sendError(response, HttpStatus.INTERNAL_SERVER_ERROR, ApiErrorMessage.UNEXPECTED_ERROR_OCCURRED.getMessage());
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Достать токен из заголовка или из cookie
     */
    private String resolveToken(HttpServletRequest request) {

        // 1. Проверяем Authorization header
        String authHeader = request.getHeader(AUTHORIZATION_HEADER);
        if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
            return authHeader.substring(BEARER_PREFIX.length());
        }

        // 2. Проверяем cookie
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (COOKIE_NAME.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }

    private void sendError(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        response.setStatus(status.value());
        response.getWriter().write(message);
    }
}






//package com.BicycleShop.security.filter;
//
//
//import com.BicycleShop.model.constants.ApiErrorMessage;
//import com.BicycleShop.security.JwtTokenProvider;
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.MalformedJwtException;
//import io.jsonwebtoken.SignatureException;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.validation.constraints.NotNull;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class JwtRequestFilter extends OncePerRequestFilter {
//    private static final String AUTHORIZATION_HEADER = "Authorization";
//    private static final String BEARER_PREFIX = "Bearer ";
//    private static final String LOGIN_PATH = "/auth/login";
//    private static final String REGISTER_PATH = "/auth/register";
//
//    private final JwtTokenProvider jwtTokenProvider;
//
//    @Override
//    protected void doFilterInternal(
//            @NotNull HttpServletRequest request,
//            @NotNull HttpServletResponse response,
//            @NotNull FilterChain filterChain)
//            throws ServletException, IOException {
//
//        Optional<String> authHeader = Optional.ofNullable(request.getHeader(AUTHORIZATION_HEADER));
//        String requestURI = request.getRequestURI();
//
//        if (authHeader.isPresent() && authHeader.get().startsWith(BEARER_PREFIX)) {
//            String jwt = authHeader.get().substring(BEARER_PREFIX.length());
//            try {
//                if (!jwtTokenProvider.validateToken(jwt)) {
//                    throw new ExpiredJwtException(null, null, ApiErrorMessage.TOKEN_EXPIRED.getMessage());
//                }
//
//                Optional<String> emailOpt = Optional.ofNullable(jwtTokenProvider.getUsername(jwt));
//                Optional<String> userIdOtp = Optional.ofNullable(jwtTokenProvider.getUserId(jwt));
//
//                if (emailOpt.isPresent() && userIdOtp.isPresent()) {
//                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
//                        List<SimpleGrantedAuthority> authorities = jwtTokenProvider.getRoles(jwt).stream()
//                                .map(SimpleGrantedAuthority::new)
//                                .collect(Collectors.toList());
//
//                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
//                                emailOpt.get(),
//                                jwt,
//                                authorities
//                        );
//                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//                    }
//                }
//
//            } catch (ExpiredJwtException e) {
//                handleTokenExpiration(requestURI, jwt, response);
//                return;
//            } catch (SignatureException | MalformedJwtException e) {
//                handleSignatureException(response);
//                return;
//            } catch (Exception e) {
//                handleUnexpectedException(response, e);
//                return;
//            }
//        }
//        filterChain.doFilter(request, response);
//    }
//
//    private void handleTokenExpiration(String requestURI, String jwt, HttpServletResponse response) throws IOException {
//        if (isAuthEndpoint(requestURI)) {
//            String refreshedToken = jwtTokenProvider.refreshToken(jwt);
//            response.setHeader(AUTHORIZATION_HEADER, BEARER_PREFIX + refreshedToken);
//        } else {
//            sendErrorResponse(response, HttpStatus.UNAUTHORIZED, ApiErrorMessage.TOKEN_EXPIRED.getMessage());
//        }
//    }
//
//    private void handleSignatureException(HttpServletResponse response) throws IOException {
//        sendErrorResponse(response, HttpStatus.UNAUTHORIZED, ApiErrorMessage.INVALID_TOKEN_SIGNATURE.getMessage());
//    }
//
//    private void handleUnexpectedException(HttpServletResponse response, Exception e) throws IOException {
//        log.error(ApiErrorMessage.ERROR_DURING_JWT_PROCESSING.getMessage(), e);
//        sendErrorResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, ApiErrorMessage.UNEXPECTED_ERROR_OCCURRED.getMessage());
//    }
//
//    private void sendErrorResponse(HttpServletResponse response, HttpStatus status, String message) throws IOException {
//        response.setStatus(status.value());
//        response.getWriter().write(message);
//    }
//
//    private boolean isAuthEndpoint(String uri) {
//        return uri.equals(LOGIN_PATH) || uri.equals(REGISTER_PATH);
//    }
//}
//
//
