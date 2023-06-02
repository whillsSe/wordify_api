package com.wordify.service.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Properties;

public class JwtTokenValidator {
    private static final JwtTokenValidator instance = new JwtTokenValidator();
    private JwtParser jwtParser;

    private JwtTokenValidator() {
        Properties prop = new Properties();
        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream input = classLoader.getResourceAsStream("config.properties")) {
            prop.load(input);
            byte[] keyBytes = prop.getProperty("jwt.key").getBytes(StandardCharsets.UTF_8);
            this.jwtParser = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(keyBytes)).build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized JwtTokenValidator getInstance() {
        return instance;
    }

    public Integer validateJwtToken(String token) throws JwtException {
        try {
            Jws<Claims> jws = jwtParser.parseClaimsJws(token);
            // Token expiry check
            if (jws.getBody().getExpiration().before(new Date())) {
                throw new ExpiredJwtException(null, null, "Token expired");
            }

            // Get user_id
            return Integer.parseInt(jws.getBody().get("user_id").toString());
        } catch (ExpiredJwtException e) {
            // If token is expired, rethrow as is
            throw e;
        } catch (SignatureException e) {
            // If signature does not match, wrap in new exception for clarity
            throw new JwtException("Signature does not match", e);
        }
    }
}
