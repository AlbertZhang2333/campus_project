package com.example.ooadgroupproject.Utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Data
@Component
public class JwtUtils {
    public static long expire=3600*24L;
    private static String secret="zyazzyaz_ooadproject_abcdefjaifhawidnaiwdnaiwdndawkdnaksdnawdadawdaw";
    private final static SecureDigestAlgorithm<SecretKey,SecretKey> algorithm = Jwts.SIG.HS256;
    public static final SecretKey key= Keys.hmacShaKeyFor(secret.getBytes());


    public String generateToken(String userMail){
        Date date=new Date();
        Date expireDate = new Date(date.getTime()+ expire*1000);
        return Jwts.builder()
                .header()
                .add("typ","JWT")
                .add("alg","HS256")
                .and()
                .claim("userMail",userMail)
                .expiration(expireDate)
                .issuedAt(new Date())
                .signWith(key, algorithm)
                .compact();

    }

    public static Jws<Claims>parseClaim(String jwt){
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(jwt);
    }
    public static JwsHeader parseHeader(String jwt){
        return parseClaim(jwt).getHeader();
    }
    public static Claims parsePayload(String jwt){
        return parseClaim(jwt).getPayload();
    }
    //检查令牌是否过期，若未过期，将返回true，过期返回false
    public boolean ExpiredCheck(Claims claims){
        return claims.getExpiration().before(new Date());
    }
    public static String getHeader(){
        return "ooad_project";
    }



}
