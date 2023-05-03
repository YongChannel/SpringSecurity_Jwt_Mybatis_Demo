package kr.co.strato.demosite.security.service;

import kr.co.strato.demosite.security.utils.HeaderUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        //Request Header에서 토큰 정보 추출
        String tokenStr = HeaderUtils.getAccessToken(request);

        //validateToken으로 토큰 유효성 검사
        if(jwtAuthenticationProvider.validateToken(tokenStr)) {
            //추출된 인증 정보를 필터링에 사용할 수 있도록 Context에 등록
            Authentication authentication = jwtAuthenticationProvider.getAuthentication(tokenStr);
            //SecurityContext에 Authentication 객체를 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}
