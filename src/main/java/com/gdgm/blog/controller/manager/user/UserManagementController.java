package com.gdgm.blog.controller.manager.user;

import com.gdgm.blog.bean.*;
import com.gdgm.blog.common.result.MemberResult;
import com.gdgm.blog.common.util.AjaxResult;
import com.gdgm.blog.common.util.Page;
import com.gdgm.blog.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Controller
@RequestMapping("/manager/user")
public class UserManagementController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRoleService memberRoleService;
    @Autowired
    private RoleService roleService;

    @RequestMapping("/toUserManagement")
    public String toUserManagement(){
        return "manager/user/usermanagement";
    }
    //加载user
    @RequestMapping("/loadUser")
    @ResponseBody
    public Object loadUser(@RequestParam(value = "pageno" ,defaultValue = "1" ,required = false) Integer pageno,
                           @RequestParam(value = "pagesize" ,defaultValue = "10" ,required = false)Integer pagesize, String keycode){
        AjaxResult result = new AjaxResult();
        try{
            Map<String,Object> map = new HashMap<String ,Object>();
            map.put("pageno",pageno);
            map.put("pagesize",pagesize);
            if(keycode != null && !keycode.equals("")){
                map.put("keycode",keycode);
            }
            Page page = memberService.queryPage(map);
            if(page.getMemberList().size() >0 ){
                result.setPage(page);
                result.setSuccess(true);
            }else{
                result.setSuccess(false);
                result.setMessage("暂无数据！");
            }
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("操作失败！");
        }
        return result;
    }
    //修改用户
    @RequestMapping("/echoeUser")
    @ResponseBody
    public Object echoeUser(Integer id){
        AjaxResult result = new AjaxResult();
        try{
            Member member = memberService.queryMemberById(id);
            if(member != null){
                Role role = roleService.queryRoleByMemberid(member.getId());
                MemberResult memberResult = new MemberResult();
                BeanUtils.copyProperties(member,memberResult);
                memberResult.setRole(role.getName());
                result.setMemberResult(memberResult);
                result.setSuccess(true);
            }else{
                result.setSuccess(false);
                result.setMessage("修改操作出现异常！");
            }

        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("操作失败！");
        }
        return result;
    }
    //修改角色
    @RequestMapping("/updateRole")
    @ResponseBody
    public Object updateRole(Integer id){
        AjaxResult result = new AjaxResult();
        try{
            Role role = roleService.queryRoleByMemberid(id);
            if(role.getName().equals("管理员")){
                result.setSuccess(false);
                result.setMessage("该用户为管理员，没有权限修改！");
            }else{
                Role r = roleService.queryRoleByName("管理员");
                Integer count = memberRoleService.updateMemberRoleByMemberid(id,r.getId());
                result.setSuccess(count == 1);
            }
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("操作失败！");
        }
        return result;
    }
    @RequestMapping("/updateUser")
    @ResponseBody
    public Object updateUser(Member member){
        AjaxResult result = new AjaxResult();
        try{
            Member member1 = memberService.queryMemberByUserName(member.getUsername());
//            Member member2 = memberService.queryMemberByEmail(member.getEmail());
            if(member1 != null){
                result.setSuccess(false);
                result.setMessage("用户名已经存在，请重新修改用户名！");
                return result;
            }else{
                Integer count = memberService.updateMemberByMemberId(member);

                result.setSuccess(count == 1);
                result.setMessage("修改成功！");
            }
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("操作失败！");
        }
        return result;
    }
    //删除用户
    @RequestMapping("/deleteUser")
    @ResponseBody
    public Object deleteUser(Integer id){
        AjaxResult result = new AjaxResult();
        try{
            Member member = memberService.queryMemberById(id);
            if(member != null){
               Boolean bool= memberService.deleteMemberById(member.getId());
               if(bool == true){
                   result.setSuccess(bool);
                   result.setMessage("删除成功！");
                   return result;
               }else{
                   result.setSuccess(false);
                   result.setMessage("删除失败！");
                   return result;
               }

            }
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("操作失败！");
        }
        return result;
    }
}
