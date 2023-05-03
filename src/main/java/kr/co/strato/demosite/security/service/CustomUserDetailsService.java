package kr.co.strato.demosite.security.service;

import kr.co.strato.demosite.security.mapper.AuthMapper;
import kr.co.strato.demosite.security.model.dto.CustomUserDetails;
import kr.co.strato.demosite.security.model.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final AuthMapper authMapper;

    //DB에 User 데이터가 존재한다면 UserDetails 객체로 만들어 리턴
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = authMapper.findByMember(username);
        if(member == null) {
            throw new UsernameNotFoundException(username + " 정보를 찾을 수 없습니다.");
        }
        return CustomUserDetails.create(member);
    }
}
