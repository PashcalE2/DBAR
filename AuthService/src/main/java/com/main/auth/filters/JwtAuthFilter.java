package com.main.auth.filters;

import com.main.auth.exeptions.BadCredentialsException;
import com.main.auth.exeptions.TokenException;
import com.main.auth.services.ClientService;
import com.main.auth.services.JwtTokenService;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {


    private final JwtTokenService jwtTokenService;
    private final ClientService clientService;

    public JwtAuthFilter(JwtTokenService jwtTokenService, ClientService clientService) {
        this.jwtTokenService = jwtTokenService;
        this.clientService = clientService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @Nonnull HttpServletResponse response,
                                    @Nonnull FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String jwtAccessToken;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwtAccessToken = authHeader.substring(7);
        } else {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            if (jwtTokenService.verifyAccessToken(jwtAccessToken)) {
                UserDetails userDetails = clientService.loadUserByUsername(
                        jwtTokenService.extractAccessSubject(jwtAccessToken)
                );
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        } catch (BadCredentialsException e) {
            throw new TokenException(e);
        }
        filterChain.doFilter(request, response);
    }
}
