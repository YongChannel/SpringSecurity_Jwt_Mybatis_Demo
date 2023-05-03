package kr.co.strato.demosite.security.model.dto;

import kr.co.strato.demosite.security.model.entity.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {
    private final String                        username;
    private final String                        password;
    private final RoleType                      roleType;
    private final Collection<GrantedAuthority>  authorities;

    //계정 만료 여부(true : 만료 안됨)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //계정 잠김 여부(true : 잠기지 않음)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //비밀번호 만료 여부(true : 만료 안됨)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //사용자 활성화 여부(true : 활성화)
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public static CustomUserDetails create(Member member) {
        return new CustomUserDetails(
                member.getUsername(),
                member.getPassword(),
                RoleType.ROLE_USER,
                Collections.singletonList(new SimpleGrantedAuthority(RoleType.ROLE_USER.getCode()))
        );
    }
}
