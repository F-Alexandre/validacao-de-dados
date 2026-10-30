package com.felipe.api.service;

import com.felipe.api.model.dto.Usuarios;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class TokenService {


    private final Key chaveSecreta = Keys.secretKeyFor(SignatureAlgorithm.HS256);

//    private Key getSignKey(){
//        return Keys.hmacShaKeyFor(chaveSecreta.getBytes(StandardCharsets.UTF_8));
//    }

    public String gerarToken(Usuarios usuario){
        return Jwts.builder()
                .setSubject(usuario.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+1000 *60 *60 *2)) //expira em duas horas
                .signWith(chaveSecreta)
                .compact();

    }


    public String validarToken(String token){

        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(chaveSecreta)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        }catch (Exception e){
            return null; //token invalido ou expirado
        }

    }

}
