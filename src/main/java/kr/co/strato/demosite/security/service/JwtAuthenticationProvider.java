package kr.co.strato.demosite.security.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import kr.co.strato.demosite.security.exception.JwtValidFailedException;
import kr.co.strato.demosite.security.model.dto.JwtTokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationProvider {
    private final           Key     key;                            //암호화 시 사용하는 키
    private static final    String  AUTHORITIES_KEY = "role";       //권한 명칭
    private static final    String  TOKEN_PREFIX    = "Bearer";     //토큰 발행 유형

    private Long accessExpiry   = 1000 * 60 * 30L;              //Access Token 유효기간(30분)
    private Long refreshExpiry  = 1000 * 60 * 60 * 24 * 14L;    //Refesh Token 유효기간(2주)

    //BASE64 디코더
    public JwtAuthenticationProvider(String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    //Authentication 객체에 포함되어 있는 권한 정보를 담은 토큰 생성
    public JwtTokenDto generateToken(Authentication authentication) {
        //권한 정보 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        //AccessToken 만료 시간
        Date accessExpiresIn = new Date(now + accessExpiry);

        //AccessToken 생성
        String accessToken = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(accessExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        //RefreshToken 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + refreshExpiry))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return JwtTokenDto.builder()
                .grantType(TOKEN_PREFIX)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessExpiresIn(accessExpiresIn.getTime())
                .build();
    }

    public JwtTokenDto reissueToken(String jwtToken) {
        Claims accessClaims = getReissueTokenClaims(jwtToken);
        String sub = accessClaims.getSubject();
        String role = (String) accessClaims.get("role");
        long now = (new Date()).getTime();
        //AccessToken 만료 시간
        Date accessExpiresIn = new Date(now + accessExpiry);

        //AccessToken 생성
        String accessToken = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(sub)
                .claim(AUTHORITIES_KEY, role)
                .setExpiration(accessExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return JwtTokenDto.builder()
                .grantType(TOKEN_PREFIX)
                .accessToken(accessToken)
                .refreshToken(null)
                .accessExpiresIn(accessExpiresIn.getTime())
                .build();
    }

    //토큰 유효성 체크
    public boolean validateToken(String jwtToken) {
        return this.getTokenClaims(jwtToken) != null;
    }

    public Claims getTokenClaims(String jwtToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwtToken)
                    .getBody();
        } catch (SecurityException e) {
            log.info("Invalid JWT signature.");
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
        }
        return null;
    }

    public Claims getExpiredTokenClaims(String jwtToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwtToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            return e.getClaims();
        }
    }

    public Claims getReissueTokenClaims(String jwtToken) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    //토큰에 권한 정보를 이용하여 Authentication 객체를 리턴
    public Authentication getAuthentication(String jwtToken) {
        if(validateToken(jwtToken)) {
            //토큰 복호화
            Claims claims = getExpiredTokenClaims(jwtToken);
            //Claims에서 권한 정보 가져오기
            Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            //UserDetails 객체를 만들어서 Authentication 리턴
            UserDetails principal = new User(claims.getSubject(), "", authorities);
            return new UsernamePasswordAuthenticationToken(principal, "", authorities);
        } else {
            throw new JwtValidFailedException(jwtToken + " 정보가 유효하지 않습니다.");
        }
    }

}
