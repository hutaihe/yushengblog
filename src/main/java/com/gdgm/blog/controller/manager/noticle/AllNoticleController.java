package com.gdgm.blog.controller.manager.noticle;


import com.gdgm.blog.common.util.AjaxResult;
import com.gdgm.blog.common.util.Page;
import com.gdgm.blog.service.NoticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/manager/noticle")
public class AllNoticleController {
    @Autowired
    private NoticleService noticleService;
    @RequestMapping("/toAllNoticle")
    public String toAllNoticle(){
        return "manager/noticle/allnoticle";
    }
    @ResponseBody
    @RequestMapping("/locadNoticle")
    public Object locadNoticle(Integer pageno,Integer pagesize,String status){
        AjaxResult result = new AjaxResult();
        try{
           Page page=  noticleService.queryPage(pageno,pagesize,status);
            result.setPage(page);
            result.setSuccess(true);
        }catch (Exception e){
            result.setMessage("查询公告失败！");
            result.setSuccess(false);
        }
        return result;
    }


}
