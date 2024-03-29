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


    public String generateToken(String userMail, String username, int identity,int userIcon){
        Date date=new Date();
        Date expireDate = new Date(date.getTime()+ expire*1000);
        return Jwts.builder()
                .header()
                .add("typ","JWT")
                .add("alg","HS256")
                .and()
                .claim("userMail",userMail)
                .claim("username",username)
                .claim("identity",identity)
                .claim("userIcon",userIcon)
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
    public boolean IfNeedFlush(Claims claims){
        Date expiration =claims.getExpiration();
        Date now=new Date();
        //如果相差时间少于一小时，刷新令牌
        if(expiration.getTime()-now.getTime()<3600*1000){
            return true;
        }else{
            return false;
        }
    }
    public static String getHeader(){
        return "passToken";
    }



}
