package com.topilmalar.service.jwt;

import com.topilmalar.entity.Users;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtServiceImpl implements JwtService{
    @Override
    public String generateAccessToken(Users byUsername) {
        Map<String, String> claims = new HashMap<>();
        claims.put("username", byUsername.getUsername());
        claims.put("id", byUsername.getId().toString());

        String token = Jwts.builder()
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5 ))
                .claims(claims)
                .subject(byUsername.getId().toString())
                .signWith(signWithKey()).compact();
        return token;
    }

    @Override
    public String extractSubject(String token){
        String id = "";
        try {
            id = Jwts.parser()
                    .verifyWith(signWithKey())
                    .build()
                    .parseSignedClaims(token).getPayload().getSubject();
        }catch (ExpiredJwtException e){
            id = "token expired";
        }
        return id;
    }

    @Override
    public String generateRefreshToken(Users users) {
        Map<String, String> claims = new HashMap<>();
        claims.put("username", users.getUsername());
        claims.put("id", users.getId().toString());

        String token = Jwts.builder()
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .claims(claims)
                .subject(users.getId().toString())
                .signWith(signWithKey()).compact();
        return token;
    }

    private SecretKey signWithKey(){
        String secretKey = "nOSeqkcWDocgABbvlerRd7lwN2r3wvvIpsoowljwPh8=";
        byte[] decode = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(decode);

    }
}
