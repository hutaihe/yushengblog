package com.gdgm.blog.controller.manager.article;


import com.gdgm.blog.bean.*;
import com.gdgm.blog.common.result.ArticleResult;
import com.gdgm.blog.common.util.AjaxResult;
import com.gdgm.blog.common.vo.ArticleVo;
import com.gdgm.blog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/manager/article")
public class WriteArticleController {
    @Autowired
    private LabelService labelService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private PictureService pictureService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleLabelService articleLabelService;
    @Autowired
    private ArticleTypeService articleTypeService;


    //到写文章界面
    @GetMapping("/toWriteArticle")
    public String toWriteArticle(){
        return "manager/article/write";
    }
    //加载标签
    @ResponseBody
    @PostMapping("/loadLabel")
    public Object loadLabel(){
        AjaxResult result = new AjaxResult();
        try {
            List<Label> alLabel= labelService.queryAllLabel();
            result.setSuccess(true);
            result.setLdata(alLabel);
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("加载标签失败！");
        }
        return result;
    }
    //加载分类
    @ResponseBody
    @PostMapping("/loadType")
    public Object loadType(HttpSession session){
        AjaxResult result = new AjaxResult();
        try {
            List<Type> typeList= typeService.queryAllType();
            Map<Integer,Type> map = new HashMap<Integer,Type>();
            List<Type> root = new ArrayList<Type>();
            for(Type type : typeList){
                map.put(type.getId(),type);
            }
            for(Type type : typeList){
                Integer pid = type.getPid();
                if(pid == null){
                    root.add(type);
                }else{
                    Type parent = map.get(type.getPid());
                    parent.getTypeList().add(type);
                }
            }
            session.setAttribute("types",root);
            result.setTypeList(root);
            result.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("加载分类失败！");
        }
        return result;
    }
    //上传文件
    @ResponseBody
    @PostMapping("/upfile")
    public Object upfile(HttpServletRequest request,String size,String filetype){
        AjaxResult result = new AjaxResult();
        try{
            MultipartHttpServletRequest mreq = (MultipartHttpServletRequest)request;

            MultipartFile mfile = mreq.getFile("upfile");

            String name = mfile.getOriginalFilename();//java.jpg
            String extname = name.substring(name.lastIndexOf(".")); // .jpg
            String iconpath = UUID.randomUUID().toString()+extname; //232243343.jpg
            //String path ="/root/javaweb/image/pic/"+iconpath;
            String path ="E:/image/thumbnail/"+iconpath;
           // String path =resourcepath+ "/static/picture/thumbnail/" +iconpath;
            mfile.transferTo(new File(path));
            Picture picture = new Picture();
            picture.setFilepath(path);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            picture.setCreatetime(simpleDateFormat.format(new Date()));
            picture.setIconpath(iconpath);
            picture.setFiletype(filetype);
            picture.setSize(size+"KB");
            int count= pictureService.savePicture(picture);
            result.setSuccess(count == 1);
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }
    //加载缩略图
    @ResponseBody
    @PostMapping("/loadThumbnail")
    public Object loadThumbnail(){
        AjaxResult result = new AjaxResult();
        try{
           List<Picture> pictureList = pictureService.queryAllPicture();
            result.setPictureList(pictureList);
            result.setSuccess(pictureList.size()>0);
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }
    //保存文章
    @ResponseBody
    @PostMapping("/saveArticle")
    public Object saveArticle(ArticleVo articleVo, HttpSession session){
        AjaxResult result = new AjaxResult();
        try{
            Member member = (Member)session.getAttribute("member");
            boolean boo = articleService.saveArticle(articleVo,member.getId());
            result.setSuccess(boo);
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }
    //查询回显文章
    @ResponseBody
    @PostMapping("/articleDetail")
    public Object articleDetail(Integer id,HttpSession session){
        AjaxResult result = new AjaxResult();
        try{
            Member member = (Member)session.getAttribute("member");
                ArticleResult articleResult = new ArticleResult();
                Article article =  articleService.queryArticleById(id);
                List<Label> labels = articleLabelService.queryLabelByArticleId(article.getId());
                List<Type> types = articleTypeService.queryTypeByArticleId(article.getId());
                Picture picture = pictureService.queryPictureById(article.getPid());
                articleResult.setId(article.getId());
                articleResult.setContent(article.getContent());
                articleResult.setTitle(article.getTitle());
                articleResult.setType(article.getType());
                articleResult.setEvaluate(article.getEvaluate());
                articleResult.setLabels(labels);
                articleResult.setTypes(types);
                articleResult.setPicture(picture);
                result.setArticleResult(articleResult);
                result.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }
    //修改文章
    @ResponseBody
    @PostMapping("/updateArticle")
    public Object updateArticle(ArticleVo articleVo, HttpSession session){
        AjaxResult result = new AjaxResult();
        try{
            Member member = (Member)session.getAttribute("member");
            boolean boo = articleService.updateArticle(articleVo,member.getId());
            result.setSuccess(boo);
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("修改文章失败！");
        }
        return result;
    }

}
