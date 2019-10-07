package com.gdgm.blog.controller.manager.noticle;

import com.gdgm.blog.bean.Noticle;
import com.gdgm.blog.common.util.AjaxResult;
import com.gdgm.blog.service.NoticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/manager/noticle")
public class ManagerNoticleController {
    @Autowired
    private NoticleService noticleService;
   @RequestMapping("/toManagerNoticle")
    public String toManagerNoticle(){
        return "manager/noticle/managernoticle";
    }
    //编辑文章
    @RequestMapping("/edit")
    public String edit(Integer id){
        return "manager/noticle/write";
    }
    //删除公告
    @ResponseBody
    @RequestMapping("/deleteNoticle")
    public Object deleteNoticle(Integer id){
        AjaxResult result = new AjaxResult();
        try {
            Noticle noticle = noticleService.queryNoticleById(id);
            if(noticle != null){
                Integer count = noticleService.deleteNoticleById(id);
                result.setSuccess(count == 1);
                result.setMessage("删除公告成功！");
            }else{
                result.setSuccess(false);
                result.setMessage("公告不存在！");
            }

        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("删除公告失败！");
        }
        return result;
    }
}
