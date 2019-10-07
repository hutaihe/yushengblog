package com.gdgm.blog.controller.potal;

import com.gdgm.blog.bean.Member;
import com.gdgm.blog.bean.MemberRole;
import com.gdgm.blog.bean.Permission;
import com.gdgm.blog.bean.Role;
import com.gdgm.blog.common.util.AjaxResult;
import com.gdgm.blog.common.util.MD5Util;
import com.gdgm.blog.service.MemberRoleService;
import com.gdgm.blog.service.MemberService;
import com.gdgm.blog.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class RegisterController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private RoleService roleService ;
    @Autowired
    private MemberRoleService memberRoleService;

    @RequestMapping("/toRegister")
    public String toRegister(){
        return "register";
    }
    @RequestMapping("/doRegister")
    @ResponseBody
    public Object doResiter(Member member) {
        AjaxResult result = new AjaxResult();
        try {
            Member member1 = memberService.queryMemberByLoginacct(member.getLoginacct());
            Member member2 = memberService.queryMemberByEmail(member.getEmail());
            if(member1 != null && member2 == null){
                result.setSuccess(false);
                result.setMessage("账号名已经存在！");
            }else if(member2 !=  null && member1 == null){
                result.setSuccess(false);
                result.setMessage("邮件已经存在！");
            }else if(member2 !=  null && member1 != null){
                result.setSuccess(false);
                result.setMessage("账号名和邮件已经存在！");
            }else{
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String format = simpleDateFormat.format(new Date());
                member.setCreatetime(format);
                member.setImage("u=2795970958,903091870&fm=26&gp=0.jpg");
                member.setUserpswd(MD5Util.digest(member.getUserpswd()));
                memberService.saveMember(member);
                Role role = roleService.queryRoleByName("投稿者");
                memberRoleService.saveMemberRole(member.getId(),role.getId());

                result.setSuccess(true);
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("注册失败！");
            e.printStackTrace();
        }
        return result;
    }
}

