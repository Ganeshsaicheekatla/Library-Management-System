package com.example.Book.Management.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Component
public class JwtAuthenticationHelper    {

    private static final  long JWT_TOKEN_VALIDITY = 60*60;

    private String secret = "thisisacodingninjasdemonstrationforsecretkeyinspringsecurityjsonwebtokenauthentication";

    public String getUsernameFromToken(String token) {

        Claims claims = getClaimsFromToken(token);
        return  claims.getSubject();
    }

    private Claims getClaimsFromToken(String token) {

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secret.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims;
    }

    public boolean isTokenExpired(String token) {
        Claims claims = getClaimsFromToken(token);

        Date expDate = claims.getExpiration();

        return !expDate.before(new Date(System.currentTimeMillis()));

    }

    public String generateToken(UserDetails userDetails){

        Map<String , Object> claims = new HashMap();

        // Extract the user's role from the authorities
        String role = userDetails.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .findFirst()  // Assuming one role per user
                .orElse("ROLE_STUDENT");  // Default role if none exists
        // Add the role to the claims map
        claims.put("role", role);
        return  Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new  Date(System.currentTimeMillis()+JWT_TOKEN_VALIDITY*1000) )
                .signWith(new SecretKeySpec(secret.getBytes() , SignatureAlgorithm.HS512.getJcaName()) , SignatureAlgorithm.HS512)
                .compact();
    }
}
