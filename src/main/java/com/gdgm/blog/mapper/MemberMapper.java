package com.gdgm.blog.mapper;


import com.gdgm.blog.bean.Member;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MemberMapper {
    public Member queryMember(Member member);
    public Member queryMemberById(Integer id);
    public Member loginMember(Member member);

    Integer updateMember(Member member);

    Member queryMemberByIdAndPassword(@Param("memberid") Integer memberid, @Param("originalpassword")String originalpassword);

    Integer updatePassword(@Param("memberid")Integer memberid, @Param("newpassword")String newpassword);

    Member queryMemberByUserName(String username);

    Member queryMemberByEmail(String email);

    void saveMember(Member member);

    Member queryMemberByLoginacct(String loginacct);

    void updatePasswordByloginacct(@Param("loginacct") String loginacct, @Param("code")String code);

    List<Member> queryMembers(@Param("ids")List<Integer> ids);
}
