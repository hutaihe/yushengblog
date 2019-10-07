package com.gdgm.blog.service.impl;


import com.gdgm.blog.bean.Member;
import com.gdgm.blog.mapper.MemberMapper;
import com.gdgm.blog.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {


    @Autowired
    private MemberMapper memberMapper;
    @Override
    public Member queryMemberById(Integer id) {
        return memberMapper.queryMemberById(id);
    }

    @Override
    public Member queryMemberByLoginacct(String loginacct) {
        return memberMapper.queryMemberByLoginacct(loginacct);
    }

    @Override
    public Member queryMemberByIdAndPassword(Integer memberid, String originalpassword) {
        return memberMapper.queryMemberByIdAndPassword(memberid,originalpassword);
    }

    @Override
    public Integer updatePassword(Integer memberid, String newpassword) {
        return memberMapper.updatePassword(memberid,newpassword);
    }

    @Override
    public Member loginMember(Member member) {
        return memberMapper.loginMember(member);
    }

    @Override
    public Integer updateMember(Member member) {
        return memberMapper.updateMember(member);
    }
    @Override
    public Member queryMemberByUserName(String username) {
        return memberMapper.queryMemberByUserName(username);
    }

    @Override
    public void saveMember(Member member) {
        memberMapper.saveMember(member);
    }

    @Override
    public void updatePasswordByloginacct(String loginacct, String code) {
        memberMapper.updatePasswordByloginacct(loginacct,code);
    }

    @Override
    public Member queryMemberByEmail(String email) {
        return memberMapper.queryMemberByEmail(email);
    }

    @Override
    public List<Member> queryMembers(List<Integer> ids) {
        return memberMapper.queryMembers(ids);
    }
}
