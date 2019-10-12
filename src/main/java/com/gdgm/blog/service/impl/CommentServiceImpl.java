package com.gdgm.blog.service.impl;
import com.gdgm.blog.bean.Article;
import com.gdgm.blog.bean.Comment;
import com.gdgm.blog.bean.Member;
import com.gdgm.blog.bean.Role;
import com.gdgm.blog.common.result.CommentResult;
import com.gdgm.blog.common.util.Page;
import com.gdgm.blog.mapper.CommentMapper;
import com.gdgm.blog.service.ArticleService;
import com.gdgm.blog.service.CommentService;
import com.gdgm.blog.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private ArticleService articleService;

    @Override
    public Integer queryAllCountByMemberid(Integer id) {
        return commentMapper.queryAllCountByMemberid(id);
    }

    @Autowired
    private RoleService roleService;

    @Override
    public Integer queryAllCount() {
        return commentMapper.queryAllCount();
    }

    @Override
    public Page quyerPage(Integer pageno, Integer pagesize, Member member,String status) {
        Page page = new Page(pageno,pagesize);
        Role role = roleService.queryRoleByMemberid(member.getId());
        Integer totalsize = null;
        List<CommentResult> commentResults = null;
        //管理员查询所有评论
        if(role.getName().equals("管理员")){
            totalsize= commentMapper.queryAdminCount(status);
           commentResults = commentMapper.queryAdminAllCount(page.getStartIndex(),pagesize,status);
        }else if(role.getName().equals("投稿者")){
            totalsize = commentMapper.queryCount(member.getId(),status);
            commentResults = commentMapper.queryComment(page.getStartIndex(),pagesize,member.getId(),status);
        }
        //投稿者查询自己的评论

        page.setTotalsize(totalsize);

        for(CommentResult commentResult :commentResults){
           Article article =  articleService.queryArticleById(commentResult.getArticleid());
           commentResult.setTitle(article.getTitle());
        }
        page.setCommentResultList(commentResults);
        return page;
    }

    @Override
    public List<CommentResult>  quyerCommentDetail(Integer id,Integer articleid) {
        return commentMapper.quyerCommentDetail(id,articleid);
    }

    @Override
    public Integer deleteComment(Integer id) {
        return commentMapper.deleteComment(id);
    }

    @Override
    public Integer saveComment(Comment comment) {
        return commentMapper.saveComment(comment);
    }

    @Override
    public CommentResult quyerCommentById(Integer id) {
        return commentMapper.quyerCommentById(id);
    }

    @Override
    public Integer updateStatus(String status, Integer id) {
        return commentMapper.updateStatus(status,id);
    }
    @Override
    public void permanentDelete(Integer id) {
        commentMapper.permanentDelete(id);
    }

    @Override
    public List<CommentResult> queryCommentByArticleid(Integer articleid) {
        return commentMapper.queryCommentByArticleid(articleid);
    }
    @Override
    public Integer updatePpid(Integer id) {
        return commentMapper.updatePpid(id);
    }

    @Override
    public Integer queryCountByPpid(Integer ppid) {
        return commentMapper.queryCountByPpid(ppid);
    }
    @Override
    public void deleteCommentByPpid(Integer ppid) {
        commentMapper.deleteCommentByPpid(ppid);
    }

    @Override
    public void deleteCommentByArticleIds(List<Integer> ids) {
        commentMapper.deleteCommentByArticleIds(ids);
    }

    @Override
    public void deleteCommentByMemberId(Integer id) {
        commentMapper.deleteCommentByMemberId(id);
    }

    @Override
    public List<Integer> quyerCommentByMemberid(Integer id) {
        return commentMapper.quyerCommentByMemberid(id);
    }
}
