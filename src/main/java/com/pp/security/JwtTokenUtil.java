package com.pp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Date;
import java.time.Clock;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtil implements Serializable {

    public static final long JWT_TOKEN_VALIDITY_DAYS = 90;

    private Clock clock;

    @Value("${jwt.signing.key}")
    private String SIGNING_KEY;

    @Value("${jwt.authorities.key}")
    private String AUTHORITIES_KEY;

    public JwtTokenUtil(Clock clock) {
        this.clock = clock;
    }

    public String getUsernameFromToken(String token){
        return getClaimFromToken(token, Claims::getSubject);
    }

    public long getExpirationDateFromToken(String token){
        return getClaimFromToken(token,Claims::getExpiration).toInstant().getEpochSecond();
    }
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver){
        var claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token){
        return Jwts.parser()
                .setSigningKey(SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token){
        var expiration = getExpirationDateFromToken(token);
        return expiration < clock.instant().getEpochSecond();
    }

    public String generateToken(UserDetails userDetails){
        var authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .claim(AUTHORITIES_KEY,authorities)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(clock.millis()))
                .setExpiration(new Date(clock.millis()+ TimeUnit.DAYS.toMillis(JWT_TOKEN_VALIDITY_DAYS)))
                .signWith(SignatureAlgorithm.HS512,SIGNING_KEY).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }




}
