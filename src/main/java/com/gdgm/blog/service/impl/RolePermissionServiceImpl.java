package com.gdgm.blog.service.impl;


import com.gdgm.blog.mapper.RolePermissionMapper;
import com.gdgm.blog.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolePermissionServiceImpl implements RolePermissionService {
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

}
