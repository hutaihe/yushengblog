package com.gdgm.blog.common.result;

import com.gdgm.blog.bean.Member;

import java.util.ArrayList;
import java.util.List;

public class CommentResult {
    private Integer id;

    private Integer memberid; //评论者id

    private Integer articleid; //文章id

    private String comment; //评论内容

    private Integer pid; //自关联id

    private String createtime; //创建时间

    private String status; //状态 0--待审核 1--已发布 2--回收站

    private String username;//评论者

    private String title ;//文章的标题

    private Integer ppid; //关联父id

    private Member member; //用户

    private String responder; // 被回复者


    private List<CommentResult> commentResults = new ArrayList<CommentResult>();

    public List<CommentResult> getCommentResults() {
        return commentResults;
    }

    public void setCommentResults(List<CommentResult> commentResults) {
        this.commentResults = commentResults;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberid() {
        return memberid;
    }

    public void setMemberid(Integer memberid) {
        this.memberid = memberid;
    }

    public Integer getArticleid() {
        return articleid;
    }

    public void setArticleid(Integer articleid) {
        this.articleid = articleid;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public Integer getPpid() {
        return ppid;
    }

    public void setPpid(Integer ppid) {
        this.ppid = ppid;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getResponder() {
        return responder;
    }

    public void setResponder(String responder) {
        this.responder = responder;
    }

}
