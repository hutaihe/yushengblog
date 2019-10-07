package com.gdgm.blog.controller.potal;


import com.gdgm.blog.bean.Member;
import com.gdgm.blog.bean.MemberRole;
import com.gdgm.blog.bean.Permission;
import com.gdgm.blog.bean.Role;
import com.gdgm.blog.common.util.AjaxResult;
import com.gdgm.blog.common.util.MD5Util;
import com.gdgm.blog.service.MemberRoleService;
import com.gdgm.blog.service.MemberService;
import com.gdgm.blog.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class LoginController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private MemberRoleService memberRoleService;

    //登录验证
    @ResponseBody
    @RequestMapping("/doLogin")
    public Object doLogin(String loginacct, String userpswd, String remember,
                          HttpSession session, HttpServletResponse response,HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        try {
            Member member = new Member();
            member.setUserpswd(MD5Util.digest(userpswd));
            member.setLoginacct(loginacct);
            Member loginmember = memberService.loginMember(member);
            if (loginmember == null) {
                result.setSuccess(false);
                result.setMessage("登录失败！");
            } else {
                MemberRole memberRole = memberRoleService.queryMemberRoleByMemeberid(loginmember.getId()); //根据用户id查询memberRole
                List<Permission> permissionResults = permissionService.queryPermissionByRoleid(memberRole.getRoleid()); //查询用户的权限
                List<Permission> root = new ArrayList<Permission>();
                Map<Integer, Permission> map = new HashMap();
                Set<String> url = new HashSet<String>();
                for (Permission permission : permissionResults) {
                    map.put(permission.getId(), permission);
                    url.add(permission.getUrl());
                }
                for (Permission permission : permissionResults) {
                    if (permission.getPid() != null) {
                        Permission parent = map.get(permission.getPid());
                        parent.getPermissionList().add(permission);
                    } else {
                        root.add(permission);
                    }
                }
               //记住密码
                if(remember.equals("0")){
                    Cookie ck1 = new Cookie("loginacct",loginacct);
                    Cookie ck2 = new Cookie("userpswd",userpswd);
                    Cookie ck3 = new Cookie("remember",remember);
                    ck1.setMaxAge(60*60*24*14);
                    response.addCookie(ck1);
                    ck2.setMaxAge(60*60*24*14);
                    response.addCookie(ck2);
                    ck3.setMaxAge(60*60*24*14);
                    response.addCookie(ck3);
                }else{
                    Cookie[] cookies = request.getCookies();
                    if(cookies != null){
                        for(Cookie cookie : cookies){
                           if(cookie.getName().equals(("loginacct"))){
                               cookie.setMaxAge(0);
                               response.addCookie(cookie);
                           }
                            if(cookie.getName().equals(("userpswd"))){
                                cookie.setMaxAge(0);
                                response.addCookie(cookie);
                            }
                            if(cookie.getName().equals(("remember"))){
                                cookie.setMaxAge(0);
                                response.addCookie(cookie);
                            }
                        }
                    }
                }
                session.setAttribute("permissionList", root); //当前用户拥有的权限
                session.setAttribute("url", url); //当前用户拥有访问的url
                result.setSuccess(true);
                session.setAttribute("member", loginmember);
            }

        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("账号或密码错误！");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("/main")
    public String main(HttpServletRequest request, HttpSession session) {
        Member member = (Member) session.getAttribute("member");
        MemberRole memberRole = memberRoleService.queryMemberRoleByMemeberid(member.getId());
        List<Permission> permissionResults = permissionService.queryPermissionByRoleid(memberRole.getRoleid());
        List<Permission> root = new ArrayList<Permission>();
        Map<Integer, Permission> map = new HashMap();
        Set<String> url = new HashSet<String>();
        for (Permission permission : permissionResults) {
            map.put(permission.getId(), permission);
            url.add(permission.getUrl());
        }
        for (Permission permission : permissionResults) {
            if (permission.getPid() != null) {
                Permission parent = map.get(permission.getPid());
                parent.getPermissionList().add(permission);
            } else {
                root.add(permission);
            }
        }
        session.setAttribute("permissionList", root);
        session.setAttribute("url", url);
        return "main";
    }
    //退出登录
    @RequestMapping("/doLogout")
    public String doLogout(HttpSession session){
        session.invalidate();
        return "redirect:/toLogin";
    }
}
