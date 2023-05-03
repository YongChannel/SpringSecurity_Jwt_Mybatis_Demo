package kr.co.strato.demosite.security.service.impl;

import io.jsonwebtoken.Claims;
import kr.co.strato.demosite.common.service.CommonService;
import kr.co.strato.demosite.security.config.SecurityConfig;
import kr.co.strato.demosite.security.mapper.AuthMapper;
import kr.co.strato.demosite.security.model.dto.AuthReqModel;
import kr.co.strato.demosite.security.model.dto.JwtTokenDto;
import kr.co.strato.demosite.security.model.dto.ReqTokenDto;
import kr.co.strato.demosite.security.model.dto.RoleType;
import kr.co.strato.demosite.security.model.entity.Member;
import kr.co.strato.demosite.security.model.entity.RefreshToken;
import kr.co.strato.demosite.security.service.AuthService;
import kr.co.strato.demosite.security.service.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthMapper authMapper;
    private final CommonService commonService;
    private final SecurityConfig securityConfig;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @Override
    public Member findByMember(String username) {
        return authMapper.findByMember(username);
    }

    @Override
    public int joinByMember(AuthReqModel authReqModel) {
        Member member = new Member();
        member.setUsername(authReqModel.getUsername());

        String rawPassword = authReqModel.getPassword();
        String encPassword = securityConfig.passwordEncoder().encode(rawPassword);
        member.setPassword(encPassword);

        member.setRegDate(commonService.getNow());
        member.setRoleType(RoleType.of("ROLE_USER"));

        return authMapper.joinByMember(member);
    }

    @Override
    public JwtTokenDto loginByMember(AuthReqModel authReqModel) {
        //사용자 정보를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authReqModel.getUsername(), authReqModel.getPassword());
        //실제로 사용자 비밀번호 검증이 이루어지는 부분
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        //인증 정보를 기반으로 JWT 생성
        JwtTokenDto jwtToken = jwtAuthenticationProvider.generateToken(authentication);

        RefreshToken refreshToken = RefreshToken.builder()
                .username(authentication.getName())
                .refreshToken(jwtToken.getRefreshToken())
                .build();

        RefreshToken res = authMapper.findByRefresh(authReqModel.getUsername());
        if(res == null) {
            authMapper.saveByRefresh(refreshToken);
        } else {
            authMapper.updateByRefresh(refreshToken);
        }
        return jwtToken;
    }

    @Override
    public JwtTokenDto reissueByToken(ReqTokenDto reqTokenDto) {
        String accessToken = reqTokenDto.getAccessToken();
        String refreshToken = reqTokenDto.getRefreshToken();

        Claims refreshClaims = jwtAuthenticationProvider.getTokenClaims(refreshToken);
        if(refreshClaims != null) {
            Claims accessClaims = jwtAuthenticationProvider.getExpiredTokenClaims(accessToken);
            if(accessClaims == null) {
                JwtTokenDto jwtToken = jwtAuthenticationProvider.reissueToken(accessToken);
                return jwtToken;
            }
        }
        return null;
    }
}
