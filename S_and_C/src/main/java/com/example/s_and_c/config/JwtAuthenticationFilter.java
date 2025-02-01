package com.example.s_and_c.config;

import com.example.s_and_c.Service.Impl.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Component needed to handle the JWT token that filter all request
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final CustomUserDetailsService userDetailsService;
    private final JwtService jwtService;

    /**
     Internal filter method that processes HTTP requests and responses in the filter chain.
     * This method is called by the filter framework for each request that needs to be filtered.
     *
     * @param request the HTTP servlet request to be processed
     * @param response the HTTP servlet response to be processed
     * @param filterChain the filter chain to execute
     * @throws ServletException if the processing fails for any servlet-specific reason
     * @throws IOException if an I/O error occurs during the processing
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        try {

            System.out.println("URL: " + request.getRequestURL());
            System.out.println("Method: " + request.getMethod());
            System.out.println("Headers: " + request.getHeaderNames());
            String jwt = getJwtFromRequest(request);
            System.out.println(jwt);
            if (StringUtils.hasText(jwt) && jwtService.validateToken(jwt)) {
                String username = jwtService.extractUsername(jwt);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    if (jwtService.validateToken(jwt, userDetails)) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                        System.out.println(authentication);

                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("User not authenticated: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Extract the jwt token from the request
     * @param request Request sent to the server from the client
     * @return token if present
     */
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}