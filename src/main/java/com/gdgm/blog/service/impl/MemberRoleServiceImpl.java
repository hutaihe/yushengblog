package com.gdgm.blog.service.impl;

import com.gdgm.blog.bean.MemberRole;
import com.gdgm.blog.bean.Role;
import com.gdgm.blog.mapper.MemberRoleMapper;
import com.gdgm.blog.service.MemberRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberRoleServiceImpl implements MemberRoleService {
    @Autowired
    private MemberRoleMapper memberRoleMapper;

    @Override
    public MemberRole queryMemberRoleByMemeberid(Integer id) {
        return memberRoleMapper.queryMemberRoleByMemeberid(id);
    }

    @Override
    public void saveMemberRole(Integer memberid,Integer roleid) {
        memberRoleMapper.saveMemberRole(memberid,roleid);
    }

    @Override
    public Integer updateMemberRoleByMemberid(Integer memberid,Integer roleid) {
        return memberRoleMapper.updateMemberRoleByMemberid(memberid,roleid);
    }

    @Override
    public void deleteMemberRoleByMemberid(Integer id) {
        memberRoleMapper.deleteMemberRoleByMemberid(id);
    }
}
