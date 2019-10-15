package com.gdgm.blog.controller.manager.message;

import com.gdgm.blog.bean.Message;
import com.gdgm.blog.common.util.AjaxResult;
import com.gdgm.blog.common.util.Page;
import com.gdgm.blog.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
@Controller
@RequestMapping("/manager/message")
public class MessageManamentController {
    @Autowired
    private MessageService messageService;
    @RequestMapping("/toMessageManament")
    public String toMessageManager(){
        return "manager/message/messagemanament";
    }
    @RequestMapping("/loadMessage")
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
            Page page = messageService.queryPage(map);
            if(page.getMessageList().size() >0 ){
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
    @RequestMapping("/eachMessage")
    @ResponseBody
    public Object eachMessage(Integer id){
        AjaxResult result = new AjaxResult();
        try{
            Message message = messageService.queryMessageByMessageid(id);
            result.setSuccess(true);
            result.setMesa(message);
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("操作失败！");
        }
        return result;
    }
    @RequestMapping("/reply")
    @ResponseBody
    public Object reply(Integer id,String reply){
        AjaxResult result = new AjaxResult();
        try{
            Integer count = messageService.updateMessageById(id,reply);
            result.setSuccess(true);
            result.setMessage("回复成功");
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("操作失败！");
        }
        return result;
    }

    @RequestMapping("/deleteMessage")
    @ResponseBody
    public Object deleteMessage(Integer id){
        AjaxResult result = new AjaxResult();
        try{
            Integer count = messageService.deleteMessageById(id);
            result.setSuccess(true);
            result.setMessage("删除成功");
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("操作失败！");
        }
        return result;
    }
}
