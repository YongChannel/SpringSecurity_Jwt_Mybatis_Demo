package kr.co.strato.demosite.security.model.entity;

import kr.co.strato.demosite.security.model.dto.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    private String      username;   //아이디
    private String      password;   //비밀번호
    private String      regDate;    //가입일
    private RoleType    roleType;   //권한
}
