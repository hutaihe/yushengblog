package com.gdgm.blog.service;

import com.gdgm.blog.bean.Role;

public interface RoleService {
    Role queryRoleByMemberid(Integer id);

    Role queryRoleByName(String name);
}
