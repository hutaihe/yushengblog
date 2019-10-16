package com.gdgm.blog.controller.potal;

import com.gdgm.blog.bean.*;
import com.gdgm.blog.common.result.ArticleResult;
import com.gdgm.blog.common.util.AjaxResult;
import com.gdgm.blog.common.util.Page;
import com.gdgm.blog.common.vo.ArticleVo;
import com.gdgm.blog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private TypeService typeService ;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleTypeService articleTypeService  ;
    @Autowired
    private LabelService labelService;
    @Autowired
    private PictureService pictureService;
    @GetMapping("/index")
    public String index(){
        return "index";
    }
    //到登录界面
    @GetMapping("/toLogin")
    public String toLogin(HttpServletResponse response, HttpServletRequest request, HttpSession session){
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
               if(cookie.getName().equals("loginacct")){
                   session.setAttribute("loginacct",cookie.getValue());
               }
                if(cookie.getName().equals("userpswd")){
                    session.setAttribute("userpswd",cookie.getValue());
                }
                if(cookie.getName().equals("remember")){
                    session.setAttribute("remember",cookie.getValue());
                }
            }
        }
        return "login";
    }
    @RequestMapping("/test")
    public String test(){
        return "test";
    }
    //加载导航条
    @ResponseBody
    @RequestMapping("/loadNav")
    public Object loadNav(){
        AjaxResult result = new AjaxResult();
        try{
            //获取所有的分类
            List<Type> types = typeService.queryAllType();
            //组装分类之间的关系
            List<Type> root = new ArrayList<>();
            Map<Integer ,Type> map = new HashMap<>();
            //将所有的type放在map集合中
            for(Type type :types){
                map.put(type.getId(),type);
            }
            //根据pid组装父子关系
            for(Type type: types){
                Type paren = type;
                if(paren.getPid()==null){
                    root.add(paren);
                }else{
                    Type t = map.get(type.getPid());
                    t.getTypeList().add(type);
                }
            }
            result.setTypeList(root);
            result.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage("加载导航条失败！");
            result.setSuccess(true);
        }
        return result;
    }
    //加载文章列表
    @ResponseBody
    @RequestMapping("/loadArticle")
    public Object loadArticle(@RequestParam(name ="pagesize" ,defaultValue ="5")Integer pagesize,
                              @RequestParam(name ="pageno" ,defaultValue ="1")Integer pageno,String keycode){
        AjaxResult result = new AjaxResult();
        try{
            Map<String,Object> map = new HashMap<>();
            map.put("pagesize",pagesize);
            map.put("pageno",pageno);
            if(keycode != null && !keycode.equals("")){
                map.put("keycode",keycode);
            }
            map.put("pagesize",pagesize);
            Page page= articleService.queryPageByMap(map);
            result.setPage(page);
            result.setSuccess(true);
        }catch(Exception e){
            e.printStackTrace();
            result.setMessage("加载文章失败！");
            result.setSuccess(false);
        }
        return result;
    }
    //到category页面
    @RequestMapping("/category")
    public String category(Integer id){
        return "category";
    }
    //根据分类id查询文章
    @ResponseBody
    @RequestMapping("/loadArticleByTypeid")
    public Object loadArticleByTypeid(@RequestParam(name ="pagesize" ,defaultValue ="5")Integer pagesize,
                              @RequestParam(name ="pageno" ,defaultValue ="1")Integer pageno,Integer id){
        AjaxResult result = new AjaxResult();
        try{
            Page page= articleService.queryPageByType(pageno,pagesize,id);
            result.setPage(page);
            result.setSuccess(true);
        }catch(Exception e){
            e.printStackTrace();
            result.setMessage("加载文章失败！");
            result.setSuccess(false);
        }
        return result;
    }
    //加载热门文章(访问量多的)
    @RequestMapping("/loadPopular")
    @ResponseBody
    public Object loadPopular(){
        AjaxResult result = new AjaxResult();
        try{
            List<Article> articles = articleService.queryArticleByAccess();

            if(articles.size()>10){
                articles = articles.subList(0,10);
                for(Article article : articles){
                    if(article.getTitle().length()>25){
                        article.setTitle(article.getTitle().substring(0,25)+"...");
                    }
                }
            }else{
                for(Article article : articles){
                    if(article.getTitle().length()>25){
                        article.setTitle(article.getTitle().substring(0,25)+"...");
                    }
                }
            }

            result.setArticleList(articles);
            result.setSuccess(true);
        }catch(Exception e){
            e.printStackTrace();
            result.setMessage("加载文章失败！");
            result.setSuccess(false);
        }
        return result;
    }
    //加载标签
    @RequestMapping("/loadLabel")
    @ResponseBody
    public Object loadLabel(){
        AjaxResult result = new AjaxResult();
        try{
            List<Label> labels = labelService.queryAllLabel();
            result.setLdata(labels);
            result.setSuccess(true);
        }catch(Exception e){
            e.printStackTrace();
            result.setMessage("加载标签失败！");
            result.setSuccess(false);
        }
        return result;
    }
    //到tag页面
    @RequestMapping("/tag")
    public String toTog(Integer id){
        return "tag";
    }
    //根据标签id查询文章
    @ResponseBody
    @RequestMapping("/loadArticleByLabelId")
    public Object loadArticleByLabelId(@RequestParam(name ="pagesize" ,defaultValue ="5")Integer pagesize,
                                      @RequestParam(name ="pageno" ,defaultValue ="1")Integer pageno,Integer id){
        AjaxResult result = new AjaxResult();
        try{
            Page page= articleService.queryPageByLabel(pageno,pagesize,id);
            result.setPage(page);
            result.setSuccess(true);
        }catch(Exception e){
            e.printStackTrace();
            result.setMessage("加载文章失败！");
            result.setSuccess(false);
        }
        return result;
    }
    //加载喜欢
    @RequestMapping("/loadLike")
    @ResponseBody
    public Object loadLike(){
        AjaxResult result = new AjaxResult();
        try{
             List<ArticleResult> articleResults = articleService.queryArticleByLike();
             result.setArticleResults(articleResults);
             result.setSuccess(true);
        }catch(Exception e){
            e.printStackTrace();
            result.setMessage("加载文章失败！");
            result.setSuccess(false);
        }
        return result;
    }

    //到author界面
    @RequestMapping("/author")
    public String author(Integer id){

        return "author";
    }
    //根据作者id查询文章
    @RequestMapping("/loadArticleByMemberid")
    @ResponseBody
    public Object loadArticleByMemberid(@RequestParam(name ="pagesize" ,defaultValue ="5")Integer pagesize,
                                       @RequestParam(name ="pageno" ,defaultValue ="1")Integer pageno,Integer id){
        AjaxResult result = new AjaxResult();
        try{
            Page page= articleService.queryPageMember(pageno,pagesize,id);
            result.setPage(page);
            result.setSuccess(true);
        }catch(Exception e){
            e.printStackTrace();
            result.setMessage("加载文章失败！");
            result.setSuccess(false);
        }
        return result;
    }
}
