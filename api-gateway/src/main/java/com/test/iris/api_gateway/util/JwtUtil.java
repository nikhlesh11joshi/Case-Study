package com.test.iris.api_gateway.util;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;
import javax.crypto.SecretKey;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    public String jwtSecret;


    public String  validateToken(final String token) {
       String isValid= String.valueOf(Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token));
        System.out.println("validateToken : "+isValid );
        return isValid;
    }



    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
