package com.gdgm.blog.mapper;


import com.gdgm.blog.bean.Permission;

import java.util.List;

public interface PermissionMapper {

    List<Permission> queryAllPermission();

    List<Permission> queryPermissionByRoleid(Integer id);
}
