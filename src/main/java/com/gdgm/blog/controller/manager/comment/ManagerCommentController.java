package com.gdgm.blog.controller.manager.comment;

import com.gdgm.blog.bean.Comment;
import com.gdgm.blog.bean.Member;
import com.gdgm.blog.common.result.CommentResult;
import com.gdgm.blog.common.util.AjaxResult;
import com.gdgm.blog.common.util.Page;
import com.gdgm.blog.service.ArticleService;
import com.gdgm.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/manager/comment")
public class ManagerCommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private ArticleService articleService;
    @RequestMapping("/toManagerComment")
    public String toManagerComment(){
        return "manager/comment/managercomment";
    }
    //分页查询评论
    @RequestMapping("locadComment")
    @ResponseBody
    public Object locadComment(Integer pageno,Integer pagesize, HttpSession session,String status){
        AjaxResult result = new AjaxResult();
        try {
            Member member = (Member)session.getAttribute("member");
            Page page = commentService.quyerPage(pageno,pagesize,member,status);
            result.setPage(page);
            result.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage("加载数据失败！");
            result.setSuccess(false);
        }
        return result;
    }
    //到回复界面
    @RequestMapping("/toReply")
    public String toReply(Integer id){
        return "manager/comment/reply";
    }
    //加载详细评论
    @RequestMapping("loadReply")
    @ResponseBody
    public Object loadReply(Integer id,Integer articleid){
        AjaxResult result = new AjaxResult();
        try {
            //根据ppid和文章id查询评论
            List<CommentResult> commentResults = commentService.quyerCommentDetail(id,articleid);
            Map<Integer,CommentResult> map = new HashMap<Integer,CommentResult>();
           //将查询所得的评论放到map中去
            for(CommentResult commentResult : commentResults){
                map.put(commentResult.getId(),commentResult);
            }
            //遍历组合父子评论
            for(CommentResult commentResul : commentResults){
                if(commentResul.getPid() != null){
                    CommentResult parent = map.get(commentResul.getPpid()); //组合父子评论
                    parent.getCommentResults().add(commentResul);
                }
            }
            result.setCommentResult(map.get(id));
            result.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage("加载数据失败！");
            result.setSuccess(false);
        }
        return result;
    }
    //评论回显
    @RequestMapping("eachCommment")
    @ResponseBody
    public Object eachCommment(Integer id){
        AjaxResult result = new AjaxResult();
        try {
            CommentResult commentResult = commentService.quyerCommentById(id);
            result.setCommentResult(commentResult);
            result.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage("加载数据失败！");
            result.setSuccess(false);
        }
        return result;
    }
    //保存评论
    @RequestMapping("saveComment")
    @ResponseBody
    public Object saveComment(Comment comment,HttpSession session){
        AjaxResult result = new AjaxResult();
        try {
            Member member = (Member)session.getAttribute("member");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            comment.setCreatetime(df.format(new Date()));
            comment.setMemberid(member.getId());
            comment.setStatus("1");
            comment.setPid(comment.getId());
            Integer count = commentService.saveComment(comment);
            result.setSuccess(count == 1);
            articleService.updateComnumByArticleid(comment.getArticleid());
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage("保存数据失败！");
            result.setSuccess(false);
        }
        return result;
    }
    //删除评论
    @RequestMapping("deleteComment")
    @ResponseBody
    public Object deleteComment(Integer id){
        AjaxResult result = new AjaxResult();
        try {
            //先查再删
            CommentResult commentResult = commentService.quyerCommentById(id);
            //如果pid为空，就删除与之相关的所有评论
            if(commentResult.getPid() == null){
               Integer count = commentService.queryCountByPpid(commentResult.getPpid());
               commentService.deleteCommentByPpid(commentResult.getPpid());
               articleService.updateReduceComnumByArticleid(commentResult.getArticleid(),count);
            }else{
                //先删除再遍历找出相关联的评论
                commentService.deleteComment(commentResult.getId());
                articleService.updateReduceComnumByArticleid(commentResult.getArticleid(),1);
                //根据ppid和文章id查询评论
                List<CommentResult> commentResults = commentService.quyerCommentDetail(commentResult.getPpid(),commentResult.getArticleid());
                Map<Integer,CommentResult> map = new HashMap<Integer,CommentResult>();
                //将查询所得的评论放到map中去
                for(CommentResult comment : commentResults){
                    map.put(comment.getId(),comment);
                }
                for(CommentResult commentResul : commentResults){
                    if(commentResul.getPid() != null){
                        //这个是为了找出已经被删除的评论以及和他相关的评论
                        CommentResult parent = map.get(commentResul.getPid());
                        //如果为空，说明相关联的评论已经被删除了
                        if(parent == null){
                            commentService.deleteComment(commentResul.getId());
                            articleService.updateReduceComnumByArticleid(commentResul.getArticleid(),1);
                            //将父评论从map中移除
                            map.remove(commentResul.getId());
                        }
                    }
                }
            }
            result.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage("删除数据失败！");
            result.setSuccess(false);
        }
        return result;
    }
    //更新状态
    @RequestMapping("updateStatus")
    @ResponseBody
    public Object updateStatus(String status,Integer id){
        AjaxResult result = new AjaxResult();
        try {
           Integer count =  commentService.updateStatus(status,id);
            result.setSuccess(count == 1);
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage("删除数据失败！");
            result.setSuccess(false);
        }
        return result;
    }
    //永久删除
    @RequestMapping("permanentDelete")
    @ResponseBody
    public Object permanentDelete(Integer id){
        AjaxResult result = new AjaxResult();
        try {
            CommentResult commentResult = commentService.quyerCommentById(id);
            Integer count = commentService.queryCountByPpid(commentResult.getPpid());
            commentService.deleteCommentByPpid(commentResult.getPpid());
            articleService.updateReduceComnumByArticleid(commentResult.getArticleid(),count);
            result.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage("删除数据失败！");
            result.setSuccess(false);
        }
        return result;
    }
}
