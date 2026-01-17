package com.sakib.practice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    private final byte[] JWT_SECRET = "fadfjal_adksf_dkfjalsdk_dkfjasdklfjakl_dlskfjakl_kldfjaklsdjf_lkdfjalksd".getBytes(StandardCharsets.UTF_8);
    private final SecretKey secretKey = Keys.hmacShaKeyFor(JWT_SECRET);

    public String generateToken(String subject){
        Map<String , String> claims = new HashMap<>();
        claims.put("role" , "ADMIN");
        claims.put("Special Access" , "true");

        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date())
                .signWith(secretKey)
                .expiration(new Date(System.currentTimeMillis() + 500000))
                .compact();
    }

    public Claims parseToken(String token){
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getUsername(String token){
        return parseToken(token).getSubject();
    }

    public String getRole(String token){
        return parseToken(token).get("role" , String.class);
    }

}
