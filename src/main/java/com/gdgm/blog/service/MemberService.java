package com.gdgm.blog.service;


import com.gdgm.blog.bean.Article;
import com.gdgm.blog.bean.Member;

import java.util.List;

public interface MemberService {
    public Member queryMemberById(Integer id);

    public Member loginMember(Member member);

    Integer updateMember(Member member);

    Member queryMemberByIdAndPassword(Integer memberid, String originalpassword);

    Integer updatePassword(Integer memberid, String newpassword);

    Member queryMemberByUserName(String username);

    Member queryMemberByEmail(String email);

    void saveMember(Member member);

    Member queryMemberByLoginacct(String loginacct);

    void updatePasswordByloginacct(String loginacct, String code);

    List<Member> queryMembers(List<Integer> ids);
}
