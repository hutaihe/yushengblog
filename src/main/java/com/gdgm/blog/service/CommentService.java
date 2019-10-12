package com.gdgm.blog.service;

import com.gdgm.blog.bean.Comment;
import com.gdgm.blog.bean.Member;
import com.gdgm.blog.common.result.CommentResult;
import com.gdgm.blog.common.util.Page;

import java.util.List;

public interface CommentService {
    Page quyerPage(Integer pageno, Integer pagesize, Member member,String status);

    List<CommentResult>  quyerCommentDetail(Integer id,Integer articleid);

    CommentResult quyerCommentById(Integer id);

    Integer saveComment(Comment comment);

    Integer deleteComment(Integer id);

    Integer updateStatus(String status, Integer id);

    void permanentDelete(Integer id);

    Integer queryAllCount();

    Integer queryAllCountByMemberid(Integer id);

    List<CommentResult> queryCommentByArticleid(Integer articleid);

    Integer updatePpid(Integer id);

    Integer queryCountByPpid(Integer ppid);

    void deleteCommentByPpid(Integer ppid);

    void deleteCommentByArticleIds(List<Integer> ids);

    void deleteCommentByMemberId(Integer id);

    List<Integer> quyerCommentByMemberid(Integer id);
}
