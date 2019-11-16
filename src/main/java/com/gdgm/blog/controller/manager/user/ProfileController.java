package com.gdgm.blog.controller.manager.user;

import com.gdgm.blog.bean.Member;
import com.gdgm.blog.bean.Role;
import com.gdgm.blog.common.result.MemberResult;
import com.gdgm.blog.common.util.AjaxResult;
import com.gdgm.blog.common.util.MD5Util;
import com.gdgm.blog.service.MemberService;
import com.gdgm.blog.service.RoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.UUID;

@Controller
@RequestMapping("/manager/user")
public class ProfileController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private RoleService roleService;
    @RequestMapping("/toProfile")
    public String toProfile(){
        return "manager/user/profile";
    }
    @RequestMapping("/localInformation")
    @ResponseBody
    public Object localInformation(HttpSession session){
        AjaxResult result = new AjaxResult();
        Member member = (Member)session.getAttribute("member");
        MemberResult memberResult = new MemberResult();
        try{
            Member member1= memberService.queryMemberById(member.getId());
            BeanUtils.copyProperties(member1,memberResult);
            Role role = roleService.queryRoleByMemberid(member.getId());
            memberResult.setRole(role.getName());
            if(member1.getImage() != null){
                memberResult.setImagePath("/root/javaweb/image/pic/"+member.getImage());
            }
            result.setMemberResult(memberResult);
            result.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage("查询数据失败！");
            result.setSuccess(false);
        }
        return result;
    }
    @RequestMapping("/updateMemberInformation")
    @ResponseBody
    public Object updateMemberInformation(HttpServletRequest request,Member member){
        AjaxResult result = new AjaxResult();
        try{
            MultipartHttpServletRequest mhsr = (MultipartHttpServletRequest)request;
            MultipartFile selectfile = mhsr.getFile("selectfile");
            if(selectfile != null){
                String name =  selectfile.getOriginalFilename();
                String extname = name.substring(name.lastIndexOf("."));
                String iconpath = UUID.randomUUID().toString()+extname;
                String path = "/root/javaweb/image/pic/"+iconpath;
                member.setImage(iconpath);
                selectfile.transferTo(new File(path));
            }
            Integer count = memberService.updateMember(member);
            result.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage("保存数据失败！");
            result.setSuccess(false);
        }
        return result;
    }
    //修改密码
    @RequestMapping("/updatePassword")
    @ResponseBody
    public Object updatePassword(Integer memberid,
                      String originalpassword,String newpassword,String confirmpassword ){
        AjaxResult result = new AjaxResult();
        try{
           Member member =  memberService.queryMemberByIdAndPassword(memberid,MD5Util.digest(originalpassword));
           if(member == null){
               result.setSuccess(false);
               result.setMessage("密码不正确！");
           }else{
               Integer count = memberService.updatePassword(memberid, MD5Util.digest(newpassword));
               result.setSuccess(count == 1);
           }
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage("修改数据失败！");
            result.setSuccess(false);
        }
        return result;
    }
}
