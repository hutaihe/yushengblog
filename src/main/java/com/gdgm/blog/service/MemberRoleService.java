package com.gdgm.blog.service;

import com.gdgm.blog.bean.MemberRole;
import com.gdgm.blog.bean.Role;

public interface MemberRoleService {

    MemberRole queryMemberRoleByMemeberid(Integer id);

    void saveMemberRole(Integer memberid,Integer roleid);

    Integer updateMemberRoleByMemberid(Integer memberid,Integer roleid);

    void deleteMemberRoleByMemberid(Integer id);
}
