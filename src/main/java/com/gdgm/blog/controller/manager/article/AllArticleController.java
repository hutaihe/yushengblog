package com.gdgm.blog.controller.manager.article;

import com.gdgm.blog.bean.Member;
import com.gdgm.blog.bean.MemberRole;
import com.gdgm.blog.bean.Role;
import com.gdgm.blog.common.result.ArticleResult;
import com.gdgm.blog.common.util.AjaxResult;
import com.gdgm.blog.common.util.Page;
import com.gdgm.blog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/manager/article")
public class AllArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleTypeService articleTypeService;
    @Autowired
    private ArticleLabelService articleLabelService;
    @Autowired
    private RoleService roleService ;
    @RequestMapping("toAll")
   public String toIndex(HttpSession session,Map<String ,Integer> map){

        return "manager/article/all";
   }
   //加载分页所有数据
    @ResponseBody
    @RequestMapping("/loadData")
    public Object loadData(Integer data, HttpSession session, @RequestParam(value ="pageno",defaultValue = "1",required =false ) Integer pageno,
                           @RequestParam(value ="pagesize",defaultValue = "5",required =false )Integer pagesize){
        AjaxResult result = new AjaxResult();
        List<ArticleResult> articleResults = new ArrayList<ArticleResult>();
        try {
            Member member =(Member)session.getAttribute("member");
            Page page = articleService.queryPageArticle(member,pageno,pagesize,data);
            result.setPage(page);
            result.setSuccess(true);

        }catch (Exception e){
            e.printStackTrace();
            result.setMessage("加载数据失败！");
            result.setSuccess(false);
        }
        return result;
    }
    //编辑文章
    @RequestMapping("/edit")
    public String edit(Integer id){
        return "manager/article/write";
    }
    //发布文章
    @RequestMapping("/release")
    @ResponseBody
    public Object release(Integer id,String status){
        AjaxResult result = new AjaxResult();
        try {
            int count = articleService.updateStatusById(id,status);
            result.setSuccess(count == 1);
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("发布文章失败！");
        }
        return result;
    }
    //丢弃文章
    @RequestMapping("/discard")
    @ResponseBody
    public Object discard(Integer id,String status){
        AjaxResult result = new AjaxResult();
        try {
            int count = articleService.updateStatusById(id,status);
            result.setSuccess(count == 1);
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("丢弃文章失败！");
        }
        return result;
    }
    //还原
    @RequestMapping("/reduction")
    @ResponseBody
    public Object reduction(Integer id,String status){
        AjaxResult result = new AjaxResult();
        try {
            int count = articleService.updateStatusById(id,status);
            result.setSuccess(count == 1);
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("还原文章失败！");
        }
        return result;
    }
    //销毁

    @RequestMapping("/destruction")
    @ResponseBody
    public Object destruction(Integer id){
        AjaxResult result = new AjaxResult();
        try {
            Integer count = articleService.deleteArticle(id);
            result.setSuccess(count == 1);
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("永久销毁文章失败！");
        }
        return result;
    }
    //下架
    @RequestMapping("/lowerShelf")
    @ResponseBody
    public Object lowerShelf(Integer id,String status){
        AjaxResult result = new AjaxResult();
        try {
            int count = articleService.updateStatusById(id,status);
            result.setSuccess(count == 1);
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("下架文章失败！");
        }
        return result;
    }
    //上架
    @RequestMapping("/upperShelf")
    @ResponseBody
    public Object upperShelf(Integer id,String status,HttpSession session){
        AjaxResult result = new AjaxResult();
        try {
            Member member = (Member)session.getAttribute("member");
            Role role = roleService.queryRoleByMemberid(member.getId());
            if(role.getName().equals("管理员")){
                int count = articleService.updateStatusById(id,status);
                result.setSuccess(count == 1);
            }else{
                result.setSuccess(false);
                result.setMessage("你没有权限上架文章！");
            }

        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("操作失败！");
        }
        return result;
    }
}

