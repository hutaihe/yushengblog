package com.gdgm.blog.service.impl;
import com.gdgm.blog.bean.Permission;
import com.gdgm.blog.mapper.PermissionMapper;
import com.gdgm.blog.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> queryAllPermission() {
        return permissionMapper.queryAllPermission();
    }

    @Override
    public List<Permission> queryPermissionByRoleid(Integer id) {
        return permissionMapper.queryPermissionByRoleid(id);
    }
}
