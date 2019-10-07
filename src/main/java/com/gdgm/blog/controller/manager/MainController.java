package com.gdgm.blog.controller.manager;

import com.gdgm.blog.bean.Member;
import com.gdgm.blog.bean.Role;
import com.gdgm.blog.common.result.DataResult;
import com.gdgm.blog.common.util.AjaxResult;
import com.gdgm.blog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


@Controller
public class MainController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private PictureService pictureService;
    @RequestMapping("/LoadData")
    @ResponseBody
    public Object LoadData(HttpSession session){
        AjaxResult result = new AjaxResult();
        Integer articles =null;
        Integer comments = null;
        Integer enclosures =  null;
        Integer days =  null;
        try{
            Member member = (Member)session.getAttribute("member");
            Role role = roleService.queryRoleByMemberid(member.getId());
            enclosures =  pictureService.queryAllCount();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(new Date());
            cal1.setTime(simpleDateFormat.parse(member.getCreatetime()));
            days= cal2.get(Calendar.DAY_OF_YEAR) - cal1.get(Calendar.DAY_OF_YEAR);


            if(role.getName().equals("管理员")){
                 articles =articleService.queryAllCount();
                 comments = commentService.queryAllCount();


            }else if(role.getName().equals("投稿者")){
                articles =  articleService.queryAllCountByMemberid(member.getId());
                comments = commentService.queryAllCountByMemberid(member.getId());
            }
            DataResult dataResult = new DataResult();
            dataResult.setArticles(articles);
            dataResult.setComments(comments);
            dataResult.setEnclosures(enclosures);
            dataResult.setCreatetime(member.getCreatetime());
            dataResult.setDays(days);
            result.setDataResult(dataResult);
            result.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage("查询数据失败！");
            result.setSuccess(false);
        }
        return result;
    }
}
