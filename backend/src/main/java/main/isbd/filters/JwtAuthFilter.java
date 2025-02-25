package main.isbd.filters;

import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.isbd.exception.BadCredentialsException;
import main.isbd.exception.TokenException;
import main.isbd.services.ActorDetailsService;
import main.isbd.services.JwtTokenService;
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
    private final ActorDetailsService actorDetailsService;

    public JwtAuthFilter(JwtTokenService jwtTokenService, ActorDetailsService actorDetailsService) {
        this.jwtTokenService = jwtTokenService;
        this.actorDetailsService = actorDetailsService;
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
                UserDetails userDetails = actorDetailsService.loadUserByUsername(jwtAccessToken);
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
