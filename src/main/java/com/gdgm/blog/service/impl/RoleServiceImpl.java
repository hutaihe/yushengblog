package com.gdgm.blog.service.impl;

import com.gdgm.blog.bean.Role;
import com.gdgm.blog.mapper.RoleMapper;
import com.gdgm.blog.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public Role queryRoleByMemberid(Integer id) {
        return roleMapper.queryRoleByMemberid(id);
    }

    @Override
    public Role queryRoleByName(String name) {
        return roleMapper.queryRoleByName(name);
    }
}

