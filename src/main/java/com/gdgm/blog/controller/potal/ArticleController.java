package com.gdgm.blog.controller.potal;

import com.gdgm.blog.bean.*;
import com.gdgm.blog.common.result.ArticleResult;
import com.gdgm.blog.common.result.CommentResult;
import com.gdgm.blog.common.util.AjaxResult;
import com.gdgm.blog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleTypeService articleTypeService;
    private @Autowired
    PictureService pictureService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private MemberService memberService;
    //到article页面
    @RequestMapping("/article")
    public String article(Integer id){
        return "article";
    }
    //正文
    @RequestMapping("/articleContent")
    @ResponseBody
    public Object articleContent(Integer id){
        AjaxResult result = new AjaxResult();
        try{
            ArticleResult articleResult = articleService.queryArticleContentById(id);
            List<Type> types = articleTypeService.queryTypeByArticleId(id); //根据文章的id查询分类
            Article next = null;
            Article last = null;
            if(types.size()>1){
                next = articleService.queryArticlesByGreaterThanId(id,types.get(1).getId()); // 根据文章的id和分类查询id比他大的id
                last = articleService.queryArticlesByLessThanId(id,types.get(1).getId()); // 根据文章的id和分类查询id比他小的id

                if(next != null && next.getTitle().length()>=40){
                    next.setTitle(next.getTitle().substring(0,37)+"...");
                }
                if(last != null && last.getTitle().length()>=40){
                    last.setTitle(last.getTitle().substring(0,37)+"...");
                }
            }else{
                next = articleService.queryArticlesByGreaterThanId(id,types.get(0).getId()); // 根据文章的id和分类查询id比他大的id
                last = articleService.queryArticlesByLessThanId(id,types.get(0).getId()); // 根据文章的id和分类查询id比他小的id
                if(next != null && next.getTitle().length()>=40){
                    next.setTitle(next.getTitle().substring(0,37)+"...");
                }
                if(last != null && last.getTitle().length()>=40){
                    last.setTitle(last.getTitle().substring(0,37)+"...");
                }
            }
            //如果查询的文章不是null就传到前端
            if(next != null){
                result.setNext(next);
            }

            //如果查询的文章不是null就传到前端
            if(last != null){
                result.setLast(last);
            }
            //查询四条相同分类的文章
            List<Article> articles = articleService.queryFiveArticle(id,types.get(0).getId());
            if(articles.size()>0 && articles!= null){
                for(Article article : articles){
                    Picture picture = pictureService.queryPictureById(article.getPid());
                    article.setPicture(picture);
                }
            }
            result.setArticleList(articles);
            result.setArticleResult(articleResult);
            result.setSuccess(true);
        }catch(Exception e){
            e.printStackTrace();
            result.setMessage("加载文章失败！");
            result.setSuccess(false);
        }
        return result;
    }
    //加载评论
    @RequestMapping("/loadComment")
    @ResponseBody
    public Object loadComments(Integer articleid){
        AjaxResult result = new AjaxResult();
       try{
            List<CommentResult> commentResults = commentService.queryCommentByArticleid(articleid);
            List<CommentResult> root = new ArrayList<CommentResult>();
            if(commentResults.size() ==0){
                result.setSuccess(false);
                result.setMessage("暂时没有评论!") ;
            }else{
                List<Integer> ids = new ArrayList<>(); //用来存储评论者的id
                Map<Integer,CommentResult> map = new HashMap<Integer,CommentResult>();
                Map<Integer,Member> membermap = new HashMap<Integer,Member>(); //用来存储评论者
                for(CommentResult commentResult : commentResults){
                    map.put(commentResult.getId(),commentResult);
                    //用来存储评论者的id(不重复)
                    if(!ids.contains(commentResult.getMemberid())){
                        ids.add(commentResult.getMemberid());
                    }
                }
                //查询评论的用户
                List<Member> members = memberService.queryMembers(ids);
                for(Member m: members ){
                    membermap.put(m.getId(),m);
                }
                //组合父评论和子评论
                for(CommentResult commentResult : commentResults){
                    commentResult.setMember(membermap.get(commentResult.getMemberid()));
                    if(commentResult.getPid() != null){
                        //储存被评论者的名字
                        Member member = membermap.get(map.get(commentResult.getPid()).getMemberid());
                        commentResult.setResponder(member.getUsername());
                        //将该评论存储在父评论中去
                        map.get(commentResult.getPpid()).getCommentResults().add(commentResult);
                    }else{
                        root.add(commentResult);
                    }
                }
                result.setConnum(map.size());
                result.setCommentResultList(root);
                result.setSuccess(true);
            }
        }catch(Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("加载评论失败!");
        }
        return result;
    }
    //提交评论
    @RequestMapping("/submitComment")
    @ResponseBody
    public Object submitComment(Integer memberid,Integer articleid,Integer pid,Integer ppid,String commentContent){
        AjaxResult result = new AjaxResult();
        try{
            Comment comment = new Comment();
            comment.setMemberid(memberid);
            comment.setArticleid(articleid);
            comment.setComment(commentContent);
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            comment.setCreatetime(sd.format(new Date()));
            comment.setStatus("1");
            //如果pid不为空，说明是子评论，需要插入pid和ppid
            if(pid != null){
                comment.setPid(pid);
                comment.setPpid(ppid);
                commentService.saveComment(comment);
            }else{
                commentService.saveComment(comment);
                Integer count = commentService.updatePpid(comment.getId());
            }
            //文章评论加1
            Integer count = articleService.updateComnumByArticleid(articleid);
            result.setSuccess(true);
        }catch(Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("加载评论失败!");
        }
        return result;
    }
    //点击喜欢
    @RequestMapping("/likeArticle")
    @ResponseBody
    public Object likeArticle(HttpServletRequest request, Integer id, HttpServletResponse response){
        AjaxResult result = new AjaxResult();
        Boolean bool = true;
        try{
            String articleName = "likearticle"+id;
            Cookie[] cookies = request.getCookies();
           for(Cookie cookie : cookies){
               //如果存在说明点过喜欢了
               if(cookie.getName().equals(articleName)){
                  if(cookie.getValue().equals(id.toString())){
                      bool = false;
                      break;
                  }
               }
           }
           if(bool == true){
                Integer count = articleService.updateLikeById(id,1);
               Cookie cookie = new Cookie(articleName,id.toString());
               cookie.setMaxAge(3600);
               cookie.setPath("/");
               response.addCookie(cookie);
           }
            result.setSuccess(bool);
        }catch(Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("操作失败!");
        }
        return result;
    }
    //点赞
    @RequestMapping("/fabulousArticle")
    @ResponseBody
    public Object fabulousArticle(HttpServletRequest request, Integer id, HttpServletResponse response){
        AjaxResult result = new AjaxResult();
        Boolean bool = true;
        try{
            String fabulousName = "fabulousArticle"+id;
            Cookie[] cookies = request.getCookies();
            for(Cookie cookie : cookies){
                //如果存在说明点过喜欢了
                if(cookie.getName().equals(fabulousName)){
                    if(cookie.getValue().equals(id.toString())){
                        bool = false;
                        break;
                    }
                }
            }
            if(bool == true){
                Integer count = articleService.updateFabulousArticleById(id,1);
                Cookie cookie = new Cookie(fabulousName,id.toString());
                cookie.setMaxAge(3600);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
            result.setSuccess(bool);
        }catch(Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("操作失败!");
        }
        return result;
    }
}
