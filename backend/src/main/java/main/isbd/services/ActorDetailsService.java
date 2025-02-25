package main.isbd.services;

import io.jsonwebtoken.Claims;
import main.isbd.exception.BadCredentialsException;
import main.isbd.sequrity.ActorDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActorDetailsService implements UserDetailsService {


    private final JwtTokenService jwtTokenService;

    public ActorDetailsService(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    public UserDetails loadUserByUsername(String accessJwt) throws UsernameNotFoundException {
        Claims claims;
        try {
            claims = jwtTokenService.extractAccessClaims(accessJwt);
        } catch (BadCredentialsException e) {
            throw new UsernameNotFoundException("Username unavailable: " + e.getMessage());
        }
        String username = claims.getSubject();
        String role = claims.get("role", String.class);
        if (username == null || role == null) {
            throw new UsernameNotFoundException("Username not found");
        }
        return new ActorDetails(username, accessJwt, List.of(role));
    }

}
