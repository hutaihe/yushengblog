package com.gdgm.blog.mapper;

import com.gdgm.blog.bean.Role;

public interface RoleMapper {

    Role queryRoleByMemberid(Integer id);

    Role queryRoleByName(String name);
}
