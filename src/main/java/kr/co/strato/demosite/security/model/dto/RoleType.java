package kr.co.strato.demosite.security.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum RoleType {
    ROLE_USER("ROLE_USER", "사용자 계정"),
    ROLE_ADMIN("ROLE_ADMIN", "관리자 계정");

    private final String code;
    private final String displayName;

    public static RoleType of(String code) {
        return Arrays.stream(RoleType.values())
                .filter(r -> r.getCode().equals(code))
                .findAny()
                .orElse(ROLE_USER);
    }
}
