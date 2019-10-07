package com.gdgm.blog.service;

import com.gdgm.blog.bean.Permission;

import java.util.List;

public interface PermissionService {

    List<Permission> queryAllPermission();

    List<Permission> queryPermissionByRoleid(Integer id);
}
