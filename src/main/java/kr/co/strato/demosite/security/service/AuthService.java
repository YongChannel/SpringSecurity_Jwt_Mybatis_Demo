package kr.co.strato.demosite.security.service;

import kr.co.strato.demosite.security.model.dto.AuthReqModel;
import kr.co.strato.demosite.security.model.dto.JwtTokenDto;
import kr.co.strato.demosite.security.model.dto.ReqTokenDto;
import kr.co.strato.demosite.security.model.entity.Member;

public interface AuthService {
    public Member findByMember(String username);

    public int joinByMember(AuthReqModel authReqModel);

    public JwtTokenDto loginByMember(AuthReqModel authReqModel);

    public JwtTokenDto reissueByToken(ReqTokenDto reqTokenDto);
}
