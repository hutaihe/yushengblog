package com.gdgm.blog.controller.potal;

import com.gdgm.blog.bean.Message;
import com.gdgm.blog.common.util.AjaxResult;
import com.gdgm.blog.common.util.Page;
import com.gdgm.blog.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
//留言
@Controller
public class MessageController {
    @Autowired
    private MessageService messageService;
    @RequestMapping("/message")
    public String toMessage(){
        return "message";
    }

    @ResponseBody
    @RequestMapping("/loadMessage")
    public Object loadArticle(@RequestParam(name ="pagesize" ,defaultValue ="5")Integer pagesize,
                              @RequestParam(name ="pageno" ,defaultValue ="1")Integer pageno){
        AjaxResult result = new AjaxResult();
        try{
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("pagesize",pagesize);
            map.put("pageno",pageno);
            Page page= messageService.loadMessage(map);
            result.setPage(page);
            result.setSuccess(true);
        }catch(Exception e){
            e.printStackTrace();
            result.setMessage("加载留言失败！");
            result.setSuccess(false);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/submitMessage")
    public Object loadArticle(Message message){
        AjaxResult result = new AjaxResult();
        try{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            message.setCreatetime(simpleDateFormat.format(new Date()));
            message.setStatus("未回复");
            messageService.saveMessage(message);
            result.setSuccess(true);
        }catch(Exception e){
            e.printStackTrace();
            result.setMessage("提交留言失败！");
            result.setSuccess(false);
        }
        return result;
    }
}

