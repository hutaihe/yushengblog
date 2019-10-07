package com.gdgm.blog.mapper;

import com.gdgm.blog.bean.MemberRole;
import com.gdgm.blog.bean.Role;
import org.apache.ibatis.annotations.Param;

public interface MemberRoleMapper {

    MemberRole queryMemberRoleByMemeberid(Integer id);

    void saveMemberRole(@Param("memberid") Integer memberid, @Param("roleid")Integer roleid);
}
