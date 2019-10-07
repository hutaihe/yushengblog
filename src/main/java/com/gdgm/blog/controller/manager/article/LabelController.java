package com.gdgm.blog.controller.manager.article;


import com.gdgm.blog.bean.ArticleLabel;
import com.gdgm.blog.bean.Label;
import com.gdgm.blog.common.util.AjaxResult;
import com.gdgm.blog.service.ArticleLabelService;
import com.gdgm.blog.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Controller
@RequestMapping("/manager/article")
public class LabelController {
    @Autowired
    private LabelService labelService;
    @Autowired
    private ArticleLabelService articleLabelService;
    //到标签页面
    @RequestMapping("/toLabel")
    public String toLabel(){
        return "manager/article/label";
    }
    //查询所有的标签
    @ResponseBody
    @RequestMapping("/queryAllLabel")
    public Object queryAllLbel(Integer id){
        AjaxResult result = new AjaxResult();
        try{
            List<Label> labelList= labelService.queryAllLabel();
            List<ArticleLabel> articleLabels= articleLabelService.queryAllArticleLabel();
            Map<Integer,Integer> map = new HashMap<Integer,Integer>();
            for(ArticleLabel articleLabel:articleLabels){
               Integer count =  map.get(articleLabel.getLabelid());
               if(count == null){
                   map.put(articleLabel.getLabelid(),1);
               }else{
                   map.put(articleLabel.getLabelid(),count+1);
               }
            }
            for(Label label : labelList){
               Integer count =  map.get(label.getId());
               if(count == null){
                   label.setCount(0);
               }else{
                   label.setCount(count);
               }
            }
            result.setLdata(labelList);
            result.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("删除分类失败！");
        }
        return result;
    }
    //添加标签
    @ResponseBody
    @RequestMapping("/addLabel")
    public Object addLabel(String labelname){
        AjaxResult result = new AjaxResult();
        try{
           //根据标签名查询标签
            Label label =  labelService.queryLabelByName(labelname);
            if(label != null){
                result.setSuccess(false);
                result.setMessage("标签已经存在!");
                return result;
            }
            Integer count = labelService.saveLabel(labelname);
            result.setSuccess(count== 1);
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("添加标签失败！");
        }
        return result;
    }
    //回显标签
    @ResponseBody
    @RequestMapping("/showLabel")
    public Object showLabel(Integer id){
        AjaxResult result = new AjaxResult();
        try{
            //根据标签名查询标签
            Label label = labelService.queryLabelById(id);
            result.setSuccess(true);
            result.setLabel(label);
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("添加标签失败！");
        }
        return result;
    }
    //修改
    @ResponseBody
    @RequestMapping("/updateLabel")
    public Object updateLabel(Label label){
        AjaxResult result = new AjaxResult();
        try{
            //根据标签名查询标签
            labelService.updateLabelById(label);
            result.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("修改标签失败！");
        }
        return result;
    }

    //删除
    @ResponseBody
    @RequestMapping("/deleteLabel")
    public Object deleteLabel(Label label){
        AjaxResult result = new AjaxResult();
        try{
            //根据标签名查询标签
            articleLabelService.deletearticleLabelByLabelid(label.getId());
            labelService.deleteLabelById(label);
            result.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("删除标签失败！");
        }
        return result;
    }
}
