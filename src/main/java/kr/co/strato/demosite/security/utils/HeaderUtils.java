package kr.co.strato.demosite.security.utils;

import javax.servlet.http.HttpServletRequest;

public class HeaderUtils {
    private final static String HEADER_AUTHORIZATION    = "Authorization";  //토큰 명칭
    private final static String TOKEN_PREFIX            = "Bearer";         //토큰 발행 유형

    //Request Header에서 토큰 정보 꺼내기
    public static String getAccessToken(HttpServletRequest request) {
        String headerValue = request.getHeader(HEADER_AUTHORIZATION);
        if(headerValue == null) {
            return null;
        }
        if(headerValue.startsWith(TOKEN_PREFIX)) {
            return headerValue.substring(TOKEN_PREFIX.length() + 1);
        }
        return null;
    }
}
