package com.gdgm.blog.controller.manager.article;


import com.gdgm.blog.bean.ArticleType;
import com.gdgm.blog.bean.Type;
import com.gdgm.blog.common.util.AjaxResult;
import com.gdgm.blog.service.ArticleTypeService;
import com.gdgm.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RequestMapping("/manager/article")
@Controller
public class CatalogueController {
    @Autowired
    private TypeService typeService;
    @Autowired
    private ArticleTypeService articleTypeService;
    //到分类目录界面
    @RequestMapping("/toCatalogue")
    public String toCatalogue(){
        return "manager/article/catalogue";
    }
    //加载分类目录
    @ResponseBody
    @RequestMapping("/load")
    public Object load(){
        AjaxResult result = new AjaxResult();
        try{
            List<Type> typeList = typeService.queryAllType();
            List<ArticleType> articleTypeList = articleTypeService.queryAllArticleType();
            List<Type> root = new ArrayList<Type>();
            Map<Integer,Type> maptype = new HashMap<Integer,Type>();
            Map<Integer,Integer> mapat = new HashMap<Integer,Integer>();
            for(Type type : typeList){
                maptype.put(type.getId(),type);
            }
            for(ArticleType articleType : articleTypeList){
                Integer count = mapat.get(articleType.getTypeid());
                if(count == null){
                    mapat.put(articleType.getTypeid(),1);
                }else{
                    mapat.put(articleType.getTypeid(),count+1);
                }
            }
            for(Type type:typeList){
                Integer count = mapat.get(type.getId());
                if(count== null){
                    type.setCount(0);
                }else{
                    type.setCount(count);
                }

                if(type.getPid() == null){
                    root.add(type);
                }else{
                    Type t=  maptype.get(type.getPid());
                    t.getTypeList().add(type);
                }
            }
            result.setTypeList(root);
            result.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("加载分类目录失败！");
        }
        return result;
    }
    //添加分类
   @ResponseBody
    @RequestMapping("/addType")
    public Object addType(Integer pid,String typename){
        AjaxResult result = new AjaxResult();
        //根据分类名字查询分类，如果存在分类了，就不能添加
        Type type =  typeService.queryTypeByName(typename);
        if(type != null){
            result.setSuccess(false);
            result.setMessage("分类已经存在！");
            return result;
        }
        try{
            if(pid == 0){
                typeService.saveType(null,typename);
            }else{
                typeService.saveType(pid,typename);
            }
            result.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("添加分类失败！");
        }
        return result;
    }
    //根据类型id查询类型
    @RequestMapping("/queryTypeById")
    @ResponseBody
    public Object queryTypeById(Integer id){
        AjaxResult result = new AjaxResult();
        try{
           Type type = typeService.queryTypeById(id);
            result.setSuccess(true);
            result.setType(type);
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("查询分类失败！");
        }
        return result;
    }
    //修改分类
    @ResponseBody
    @RequestMapping("/updateType")
    public Object updateType(Type type){
        AjaxResult result = new AjaxResult();
        try{
            if(type.getPid() == 0){
                type.setPid(null);
            }
           Integer count  = typeService.updateTypeById(type);
            result.setSuccess(count == 1);
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("添加分类失败！");
        }
        return result;
    }
    //根据id删除分类
    @ResponseBody
    @RequestMapping("/deleteType")
    public Object deleteType(Integer id){
        AjaxResult result = new AjaxResult();
        try{
            //根据typeid查询是否有子分类
           List<Type> types =  typeService.queryTyepByPid(id);
           if(types.size()>0&& types != null){
               result.setSuccess(false);
               result.setMessage("请先删除子分类！");
           }else{
               //根据typeid删除articleType
               articleTypeService.deleteArticleTypeByTypeId(id);
               //根据id删除分类
               Integer count = typeService.deleteTypeById(id);
               result.setSuccess(true);
           }
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("删除分类失败！");
        }
        return result;
    }
}
