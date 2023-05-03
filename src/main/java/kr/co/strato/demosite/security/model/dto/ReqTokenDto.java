package kr.co.strato.demosite.security.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "ReqTokenDto", description = "재발급 토큰 정보 객체")
public class ReqTokenDto {
    @ApiModelProperty(value = "Access Token", example = "aaa.bbb.ccc")
    private String accessToken;
    @ApiModelProperty(value = "Refresh Token", example = "aaa.bbb.ccc")
    private String refreshToken;
}
