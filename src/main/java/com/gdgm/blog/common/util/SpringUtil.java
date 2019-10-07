package com.gdgm.blog.common.util;

import com.gdgm.blog.bean.Permission;
import com.gdgm.blog.bean.Role;
import com.gdgm.blog.service.PermissionService;
import com.gdgm.blog.service.RoleService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 将角色的权限放到上下文对象中去
 */
@Component
public class SpringUtil implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        // 获取application域
        ServletContext servletContext = servletContextEvent.getServletContext();
        ApplicationContext ioc = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        PermissionService permissionService = ioc.getBean(PermissionService.class);
        List<Permission> permissionList = permissionService.queryAllPermission();
        Set<String> set = new HashSet<String>();
        for(Permission permission: permissionList){
            set.add(permission.getUrl());
        }
        servletContext.setAttribute("AllPermission",set);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
