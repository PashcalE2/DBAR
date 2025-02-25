package main.isbd.services;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import main.isbd.exception.BadCredentialsException;
import main.isbd.exception.BaseAppException;
import main.isbd.repositories.AdminRepository;
import main.isbd.repositories.ClientRepository;
import main.isbd.repositories.FactoryRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Set;

@Service
public class JwtTokenService {

    private final PublicKey publicAccessKey;
    private final ClientRepository clientRepository;
    private final AdminRepository adminRepository;
    private final FactoryRepository factoryRepository;
    private final Set<String> roles = Set.of("ROLE_ADMIN", "ROLE_CLIENT", "ROLE_FACTORY");

    public JwtTokenService(@Value("${jwt.secret.access.public}") String publicAccessKey,
                           ClientRepository clientRepository, AdminRepository adminRepository,
                           FactoryRepository factoryRepository) {
        this.publicAccessKey = getPublicKey(publicAccessKey);
        this.clientRepository = clientRepository;
        this.adminRepository = adminRepository;
        this.factoryRepository = factoryRepository;
    }

    public String extractLoginWithAvailabilityCheck(String accessToken, String requiredRole) throws BaseAppException {
        Claims claims = extractAccessClaims(accessToken);
        String subject = claims.getSubject();
        String role = claims.get("role", String.class);
        if (subject == null || role == null || !roles.contains(role)) {
            throw new BadCredentialsException("Unexpected claims in jwt");
        }
        if (!role.equals(requiredRole)) {
            throw new BaseAppException(role + " cannot register as " + requiredRole, HttpStatus.FORBIDDEN);
        }
        switch (requiredRole) {
            case "ROLE_CLIENT" -> {
                return availabilityCheck(clientRepository.findByName(subject).isEmpty(), subject);
            }
            case "ROLE_ADMIN" -> {
                return availabilityCheck(adminRepository.findByLogin(subject).isEmpty(), subject);
            }
            case "ROLE_FACTORY" -> {
                return availabilityCheck(factoryRepository.findByLogin(subject).isEmpty(), subject);
            }
            default -> throw new BaseAppException("Role management failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private String availabilityCheck(boolean isAvailable, String subject) throws BaseAppException {
        if (isAvailable) {
            return subject;
        } else {
            throw new BaseAppException("Username is already occupied", HttpStatus.CONFLICT);
        }
    }

    public Boolean verifyAccessToken(String accessToken) throws BadCredentialsException {
        Claims claims = extractAccessClaims(accessToken);
        String subject = claims.getSubject();
        String role = claims.get("role", String.class);
        if (subject == null || role == null) {
            return false;
        }
        switch (role) {
            case "ROLE_CLIENT" -> {
                return clientRepository.findByName(subject).isPresent();
            }
            case "ROLE_ADMIN" -> {
                return adminRepository.findByLogin(subject).isPresent();
            }
            case "ROLE_FACTORY" -> {
                return factoryRepository.findByLogin(subject).isPresent();
            }
            case "ROLE_SUPERVISOR" -> {
                return true;
            }
            default -> {
                return false;
            }
        }

    }

    public Claims extractAccessClaims(String accessToken) throws BadCredentialsException {
        Claims claims;
        try {
            claims = Jwts.parser().verifyWith(this.publicAccessKey).build().parseSignedClaims(accessToken).getPayload();
        } catch (ExpiredJwtException e) {
            throw new BadCredentialsException("Expired access token");
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException e) {
            throw new BadCredentialsException("Invalid access token");
        } catch (Exception e) {
            throw new BadCredentialsException("Wrong access token");
        }
        return claims;
    }


    private PublicKey getPublicKey(String key) {
        byte[] keyBytes = Base64.getDecoder().decode(key);
        KeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        PublicKey publicKey;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        return publicKey;
    }

}
