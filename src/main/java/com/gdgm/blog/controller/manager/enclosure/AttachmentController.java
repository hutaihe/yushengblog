package com.gdgm.blog.controller.manager.enclosure;

import com.gdgm.blog.bean.Picture;
import com.gdgm.blog.common.util.AjaxResult;
import com.gdgm.blog.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/manager/enclosure")
public class AttachmentController {
    @Autowired
    private PictureService pictureService;
    @RequestMapping("/toAttachment")
    public String toAttachment(){
        return "manager/enclosure/attachment";
    }
    @RequestMapping("/loadEnclosure")
    @ResponseBody
    public Object loadEnclosure(String iconpath){
        AjaxResult result = new AjaxResult();
        List<Picture> pictures = null;
        try {
            if(iconpath != null){
                pictures = pictureService.queryAllPictureByIconpath(iconpath);
            }else{
                pictures = pictureService.queryAllPicture();
            }

            result.setPictureList(pictures);
            result.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage("加载数据失败！");
            result.setSuccess(false);
        }
        return result;
    }
    //图片详情
    @RequestMapping("/pictureDetail")
    @ResponseBody
    public Object pictureDetail(Integer id){
        AjaxResult result = new AjaxResult();
        try {
            Picture picture = pictureService.queryPictureById(id);
            result.setPicture(picture);
            result.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage("加载数据失败！");
            result.setSuccess(false);
        }
        return result;
    }
    //删除图片
    @RequestMapping("/deletePicture")
    @ResponseBody
    public Object deletePicture(Integer id){
        AjaxResult result = new AjaxResult();
        try {
            Integer count  = pictureService.deletePictureById(id);
            result.setSuccess(count == 1);
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage("删除数据失败！");
            result.setSuccess(false);
        }
        return result;
    }
}
