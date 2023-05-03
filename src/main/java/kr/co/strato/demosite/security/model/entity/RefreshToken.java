package kr.co.strato.demosite.security.model.entity;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {
    private String username;
    private String refreshToken;

    public RefreshToken updateToken(String token) {
        this.refreshToken = token;
        return this;
    }
}
