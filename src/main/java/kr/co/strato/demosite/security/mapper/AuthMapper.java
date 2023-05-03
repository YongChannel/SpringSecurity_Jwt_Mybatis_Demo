package kr.co.strato.demosite.security.mapper;

import kr.co.strato.demosite.security.model.entity.Member;
import kr.co.strato.demosite.security.model.entity.RefreshToken;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthMapper {
    Member findByMember(String username);

    int joinByMember(Member member);

    RefreshToken findByRefresh(String username);

    int saveByRefresh(RefreshToken refreshToken);

    int updateByRefresh(RefreshToken refreshToken);
}
