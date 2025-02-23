package com.main.auth.services;

import com.main.auth.exeptions.BadCredentialsException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;


@Slf4j
@Service
public class JwtTokenService {

    private final PrivateKey privateAccessKey;
    private final PublicKey publicAccessKey;
    private final PrivateKey privateRefreshKey;
    private final PublicKey publicRefreshKey;
    private final ClientService clientService;

    public JwtTokenService(@Value("${jwt.secret.access.private}") String privateAccessKey,
                           @Value("${jwt.secret.access.public}") String publicAccessKey,
                           @Value("${jwt.secret.refresh.private}") String privateRefreshKey,
                           @Value("${jwt.secret.refresh.public}") String publicRefreshKey,
                           ClientService clientService) {
        this.privateAccessKey = getPrivateKey(privateAccessKey);
        this.publicAccessKey = getPublicKey(publicAccessKey);
        this.privateRefreshKey = getPrivateKey(privateRefreshKey);
        this.publicRefreshKey = getPublicKey(publicRefreshKey);
        this.clientService = clientService;
    }

    public String generateAccessToken(String userName, String role) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 900000L);
        //Date expiryDate = new Date(now.getTime() + 60000L);

        return Jwts.builder()
                .subject(String.valueOf(userName))
                .claim("role", role)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(privateAccessKey, Jwts.SIG.RS256)
                .compact();
    }

    public String generateAccessToken(String refreshToken) throws BadCredentialsException {
        Claims claims;
        try {
            claims = Jwts.parser().verifyWith(this.publicRefreshKey).build().
                    parseSignedClaims(refreshToken).getPayload();
        } catch (ExpiredJwtException e) {
            throw new BadCredentialsException("Expired refresh token");
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException e) {
            throw new BadCredentialsException("Invalid refresh token");
        } catch (Exception e) {
            throw new BadCredentialsException("Wrong refresh token");
        }
        String subject = claims.getSubject();
        try {
            clientService.loadUserByUsername(subject);
        } catch (UsernameNotFoundException e) {
            throw new BadCredentialsException("No such user!");
        }
        return generateAccessToken(subject, claims.get("role", String.class));
    }

    public String generateRefreshToken(String userName, String role) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 2592000000L);
        //Date expiryDate = new Date(now.getTime() + 180000L);

        return Jwts.builder()
                .subject(String.valueOf(userName))
                .claim("role", role)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(privateRefreshKey, Jwts.SIG.RS256)
                .compact();
    }

    public Boolean verifyAccessToken(String accessToken) throws BadCredentialsException {
        String username;
        try {
            username = extractAccessSubject(accessToken);
            clientService.loadUserByUsername(username);
        } catch (JwtException | IllegalArgumentException | UsernameNotFoundException e) {
            log.debug("{}: {}", e.getClass(), e.getMessage());
            return false;
        }
        return true;
    }

    public String extractAccessSubject(String accessToken) throws BadCredentialsException {
        Claims claims = extractAccessClaims(accessToken);
        return claims.getSubject();
    }

    private Claims extractAccessClaims(String accessToken) throws BadCredentialsException {
        Claims claims;
        try {
            claims = Jwts.parser().verifyWith(this.publicAccessKey).build().parseSignedClaims(accessToken).getPayload();
        } catch (ExpiredJwtException e) {
            throw new BadCredentialsException("Expired access token");
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException e) {
            log.error("Invalid access token: {}: {}", e.getClass(), e.getMessage());
            throw new BadCredentialsException("Invalid access token");
        } catch (Exception e) {
            log.error("Wrong access token: {}: {}", e.getClass(), e.getMessage());
            throw new BadCredentialsException("Wrong access token");
        }
        return claims;
    }

    private PrivateKey getPrivateKey(String key) {
        byte[] keyBytes = Base64.getDecoder().decode(key);
        KeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        PrivateKey privateKey;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            privateKey = keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        return privateKey;
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
