package kr.co.strato.demosite.security.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "JwtTokenDto", description = "토큰 정보 객체")
public class JwtTokenDto {
    @ApiModelProperty(value = "토큰 발급 유형", example = "Bearer")
    private String  grantType;
    @ApiModelProperty(value = "Access Token", example = "aaa.bbb.ccc")
    private String  accessToken;
    @ApiModelProperty(value = "Refresh Token", example = "aaa.bbb.ccc")
    private String  refreshToken;
    @ApiModelProperty(value = "Access Token 만료 시간", example = "1000")
    private Long    accessExpiresIn;
}
