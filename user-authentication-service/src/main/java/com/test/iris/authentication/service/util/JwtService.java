package com.test.iris.authentication.service.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

@Component
public class JwtService {

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);
    @Value("${jwt.secret}")
    private  String jwtRefreshSecret;
    @Value("${jwt.secret}")
    private  String jwtSecret;
    @Value("${jwt.expiration}")
    private int JwtExpirationMs;

    @Value("${jwt.refreshExpirationMs}")
    private int jwtRefreshExpirationMs;

    public String generateToken(String username) {
        Map<String,Object> claims = new HashMap<>();
    logger.info("Generating token for user : {}",username);
    logger.info("jwtSecret : {}",jwtSecret);
        return createToken(claims,username);
    }

    private String createToken(Map<String,Object> claims,String username) {
        return Jwts.builder().setClaims(claims).setSubject(username).
                issuedAt(new Date(System.currentTimeMillis())).expiration(new Date(new Date().getTime()+JwtExpirationMs)).
                signWith(SignatureAlgorithm.HS256,jwtSecret).compact();
    }

    public String generateJwtRefreshToken(String username) {
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtRefreshExpirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload().getSubject();
    }

    public String getUserNameFromJwtRefreshToken(String token) {
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload().getSubject();
    }


    public boolean validateJwtToken(
            String authToken) throws SignatureException, MalformedJwtException, ExpiredJwtException, UnsupportedJwtException, IllegalArgumentException {
        try {

            Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
            throw e;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
            throw e;
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
            throw e;
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
            throw e;
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
            throw e;
        }
    }

    public boolean validateJwtRefreshToken(
            String authToken) throws SignatureException, MalformedJwtException, ExpiredJwtException, UnsupportedJwtException, IllegalArgumentException {
        try {
            Jwts.parser().setSigningKey(jwtRefreshSecret).build().parseSignedClaims(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid refresh signature: {}", e.getMessage());
            throw e;
        } catch (MalformedJwtException e) {
            logger.error("Invalid refresh token: {}", e.getMessage());
            throw e;
        } catch (ExpiredJwtException e) {
            logger.error("Refresh token is expired: {}", e.getMessage());
            throw e;
        } catch (UnsupportedJwtException e) {
            logger.error("Refresh token is unsupported: {}", e.getMessage());
            throw e;
        } catch (IllegalArgumentException e) {
            logger.error("Refresh claims string is empty: {}", e.getMessage());
            throw e;
        }
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
