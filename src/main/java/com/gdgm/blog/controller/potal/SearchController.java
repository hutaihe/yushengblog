package com.gdgm.blog.controller.potal;

import com.gdgm.blog.common.util.AjaxResult;
import com.gdgm.blog.common.util.Page;
import com.gdgm.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SearchController {
    @Autowired
    private ArticleService articleService;
    @RequestMapping("/search")
    public String toSearch(String keycode){
        return "search";
    }
    //搜索文章
    @ResponseBody
    @RequestMapping("/doSearch")
    public Object doSearch(@RequestParam(value ="pageno",defaultValue = "1",required =false ) Integer pageno,
                           @RequestParam(value ="pagesize",defaultValue = "10",required =false )Integer pagesize, String keycode){
        AjaxResult result = new AjaxResult();
        try {
            Page page = articleService.queryPageByKeyCode(pageno,pagesize,keycode);
            if(page.getArticleResults().size() == 0){
                result.setSuccess(false);
                result.setMessage("找不到类似文章！");
            }else{
                result.setSuccess(true);
                result.setPage(page);
            }
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("操作失败！");
        }
        return result;
    }
}
