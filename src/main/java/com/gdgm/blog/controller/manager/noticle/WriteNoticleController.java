package com.gdgm.blog.controller.manager.noticle;

import com.gdgm.blog.bean.Noticle;
import com.gdgm.blog.common.util.AjaxResult;
import com.gdgm.blog.service.NoticleService;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/manager/noticle")
public class WriteNoticleController {
    @Autowired
    private NoticleService noticleService;
    //到写公告界面
    @RequestMapping("/toWrite")
    public String toWrite(){
        return "/manager/noticle/write";
    }
    //保存公告
    @ResponseBody
    @RequestMapping("/saveNoticle")
    public Object saveNoticle(Noticle noticle){
        AjaxResult result = new AjaxResult();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        String date = simpleDateFormat.format(new Date());
        try {
            noticle.setAccess(0);
            noticle.setCreationtime(date);
            int count = noticleService.saveNoticle(noticle);
            result.setSuccess(count == 1);
        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage("保存文章失败！");
        }
        return result;
    }
    //更新公告状态
    @ResponseBody
    @RequestMapping("/updateStatus")
    public Object updateStatus(Noticle noticle){
        AjaxResult result = new AjaxResult();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        String date = simpleDateFormat.format(new Date());
        try {
            int count = noticleService.updateStatus(noticle);
            result.setSuccess(count == 1);
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("操作失败！");
        }
        return result;
    }
    //文章回显
    @ResponseBody
    @RequestMapping("/noticleDetail")
    public Object noticleDetail(Integer id){
        AjaxResult result = new AjaxResult();
        try {
            Noticle noticle = noticleService.queryNoticleById(id);
            if(noticle == null){
                result.setSuccess(false);
                result.setMessage("没有此公告！");
            }else{
                result.setSuccess(true);
                result.setNoticle(noticle);
            }
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("回显公告失败！");
        }
        return result;
    }
    //修改公告
    @ResponseBody
    @RequestMapping("/updateNoticle")
    public Object updateNoticle(Noticle noticle){
        AjaxResult result = new AjaxResult();
        try {
            Integer count = noticleService.updateNoticleById(noticle);
            result.setSuccess(count == 1);
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("修改公告失败！");
        }
        return result;
    }
}
