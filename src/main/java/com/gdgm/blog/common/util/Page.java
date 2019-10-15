package com.gdgm.blog.common.util;
import com.gdgm.blog.bean.Member;
import com.gdgm.blog.bean.Message;
import com.gdgm.blog.bean.Noticle;
import com.gdgm.blog.common.result.ArticleResult;
import com.gdgm.blog.common.result.CommentResult;
import com.gdgm.blog.common.result.MessageResult;

import java.util.List;

/**
 * 封装分页数据
 */
public class Page {

    private Integer pageno; //查第几页

    private Integer pagesize; //一页几条

    private Integer totalno; //多少页

    private Integer totalsize; //多少条数据

    private ArticleResult articleResult;

    private List<ArticleResult> articleResults;

    private List<Noticle> noticleList;

    private CommentResult commentResult;

    private List<CommentResult> commentResultList;

    private List<Member> memberList ;

    private List<Message> messageList;

    private List<MessageResult> messageResultList;

    public CommentResult getCommentResult() {
        return commentResult;
    }

    public void setCommentResult(CommentResult commentResult) {
        this.commentResult = commentResult;
    }

    public List<CommentResult> getCommentResultList() {
        return commentResultList;
    }

    public void setCommentResultList(List<CommentResult> commentResultList) {
        this.commentResultList = commentResultList;
    }

    public Page(Integer pageno, Integer pagesize){
        if(pageno <=0){
            pageno = 1;
        }else{
            this.pageno = pageno;
        }
        if(pagesize <=1){
            pagesize = 5;
        }else{
            this.pagesize = pagesize;
        }
    }

    public Integer getPageno() {
        return pageno;
    }

    public void setPageno(Integer pageno) {
        this.pageno = pageno;
    }

    public Integer getPagesize() {
        return pagesize;
    }

    public void setPagesize(Integer pagesize) {
        this.pagesize = pagesize;
    }

    public Integer getTotalno() {

        return totalno;
    }

    public void setTotalno(Integer totalno) {
        this.totalno = totalno;
    }

    public Integer getTotalsize() {
        return totalsize;
    }

    public void setTotalsize(Integer totalsize) {
        this.totalsize = totalsize;
        this.totalno = (totalsize % pagesize) == 0 ? (totalsize / pagesize) : (totalsize / pagesize) + 1;

    }

    //获取起始位置
    public Integer getStartIndex(){
        Integer startIndex = (pageno-1) * pagesize;
        return startIndex;
    }

    public List<ArticleResult> getArticleResults() {
        return articleResults;
    }

    public void setArticleResults(List<ArticleResult> articleResults) {
        this.articleResults = articleResults;
    }

    public List<Noticle> getNoticleList() {
        return noticleList;
    }

    public void setNoticleList(List<Noticle> noticleList) {
        this.noticleList = noticleList;
    }

    public ArticleResult getArticleResult() {
        return articleResult;
    }

    public void setArticleResult(ArticleResult articleResult) {
        this.articleResult = articleResult;
    }

    public List<Member> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<Member> memberList) {
        this.memberList = memberList;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    public List<MessageResult> getMessageResultList() {
        return messageResultList;
    }

    public void setMessageResultList(List<MessageResult> messageResultList) {
        this.messageResultList = messageResultList;
    }
}
