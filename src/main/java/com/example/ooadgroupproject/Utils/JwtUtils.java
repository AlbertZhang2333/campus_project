package com.example.ooadgroupproject.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
@ConfigurationProperties(prefix="utils-config.jwt")
public class JwtUtils {
    private long expire;
    private String secret;
    private String header;

    public String generateToken(String userId){
        Date date=new Date();
        Date expireDate = new Date(date.getTime()+ 1000*expire);
        return Jwts.builder()
                .setHeaderParam("typ","JWT")
                .setSubject(userId)
                .setIssuedAt(date)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }
    public Claims getClaimsByToken(String jwt){
        try{
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJwt(jwt)
                    .getBody();
        }catch (Exception e){
            System.out.println("token认证错误");
            return null;
        }
    }
    public boolean ExpiredCheck(Claims claims){
        return claims.getExpiration().before(new Date());
    }
}
