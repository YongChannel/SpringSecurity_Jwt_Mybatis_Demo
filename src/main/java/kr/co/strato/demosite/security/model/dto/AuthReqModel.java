package kr.co.strato.demosite.security.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "AuthReqModel", description = "사용자 정보 객체")
public class AuthReqModel {
    @ApiModelProperty(value = "아이디", example = "user")
    private String username;
    @ApiModelProperty(value = "비밀번호", example = "user1234")
    private String password;
}
