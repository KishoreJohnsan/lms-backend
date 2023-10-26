package com.lms.authservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {

    @Autowired
    Environment env;

    public Map<String, Object> generateToken(String username) {

        Map<String, Object> map = new HashMap<>();
        String validity = env.getProperty("jwt.token.validity");
        Map<String, Object> claims = new HashMap<>();

        Date issueDate = new Date();
        Date expiryDate = new Date(issueDate.getTime() + (Long.parseLong(validity) * 1000));

        String token =  Jwts.builder().setClaims(claims)
                .setSubject(username).setIssuedAt(issueDate).setExpiration(expiryDate)
                .signWith(getKey(), SignatureAlgorithm.HS256).compact();

        map.put("token", token);
        map.put("expiry", expiryDate);
        return map;
    }

    public Claims getClaimsFromToken(String token){
        return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
    }

    public boolean validateToken(String token, UserDetails user){

        Claims claims = getClaimsFromToken(token);
        String username = claims.getSubject();
        Date expiryDate = claims.getExpiration();
        return (user.getUsername().equalsIgnoreCase(username) && expiryDate.after(new Date()));
    }

    private SecretKey getKey(){
        String secret = env.getProperty("jwt.secret");
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }
}
