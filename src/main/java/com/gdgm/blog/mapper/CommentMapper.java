package com.gdgm.blog.mapper;

import com.gdgm.blog.bean.Comment;
import com.gdgm.blog.common.result.CommentResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentMapper {
    List<CommentResult>  queryComment(@Param("pageno")Integer pageno, @Param("pagesize")Integer pagesize,@Param("id")Integer id,@Param("status")String status);

    Integer queryCount(@Param("id") Integer id,@Param("status") String status);

    List<CommentResult>  quyerCommentDetail(@Param("id")Integer id,@Param("articleid")Integer articleid);

    CommentResult quyerCommentById(Integer id);

    Integer saveComment(Comment comment);

    Integer deleteComment(Integer id);

    Integer updateStatus(@Param("status")String status, @Param("id")Integer id);

    void permanentDelete(Integer id);

    Integer queryAdminCount(@Param("status")String status);

    List<CommentResult> queryAdminAllCount(@Param("pageno")Integer pageno, @Param("pagesize")Integer pagesize,@Param("status")String status);

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
