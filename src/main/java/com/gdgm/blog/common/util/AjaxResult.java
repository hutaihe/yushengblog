package com.gdgm.blog.common.util;



import com.gdgm.blog.bean.*;
import com.gdgm.blog.common.result.ArticleResult;
import com.gdgm.blog.common.result.CommentResult;
import com.gdgm.blog.common.result.DataResult;
import com.gdgm.blog.common.result.MemberResult;

import java.util.List;

public class AjaxResult {

    private boolean success;

    private String message;

    private List<Label> ldata ;

    private List<Type> typeList;

    private List<Picture> pictureList;

    private List<Article> articleList ;

    private ArticleResult articleResult;

    private List<ArticleResult> articleResults;

    private Article last;

    private Article next;

    private Picture picture;

    private DataResult dataResult;

    private Page page;

    private Type type;

    private Label label;

    private Noticle noticle;

    private MemberResult memberResult;

    private CommentResult commentResult;

    private List<CommentResult> commentResultList;

    private  List<Permission> permissionList;

    private Integer connum;

    private  Message mesa;

    private Integer menum; //文章数

    public Integer getMenum() {
        return menum;
    }

    public void setMenum(Integer menum) {
        this.menum = menum;
    }

    public Integer getArnum() {
        return arnum;
    }

    public void setArnum(Integer arnum) {
        this.arnum = arnum;
    }

    private Integer arnum;//留言数

    public DataResult getDataResult() {
        return dataResult;
    }

    public void setDataResult(DataResult dataResult) {
        this.dataResult = dataResult;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }


    public MemberResult getMemberResult() {
        return memberResult;
    }

    public void setMemberResult(MemberResult memberResult) {
        this.memberResult = memberResult;
    }



    public CommentResult getCommentResult() {
        return commentResult;
    }

    public void setCommentResult(CommentResult commentResult) {
        this.commentResult = commentResult;
    }

    public Noticle getNoticle() {
        return noticle;
    }

    public void setNoticle(Noticle noticle) {
        this.noticle = noticle;
    }

    private  List<ArticleResult> articleResultList ;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Label> getLdata() {
        return ldata;
    }

    public void setLdata(List<Label> ldata) {
        this.ldata = ldata;
    }

    public List<Type> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<Type> typeList) {
        this.typeList = typeList;
    }

    public List<Picture> getPictureList() {
        return pictureList;
    }

    public void setPictureList(List<Picture> pictureList) {
        this.pictureList = pictureList;
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }

    public List<ArticleResult> getArticleResultList() {
        return articleResultList;
    }

    public void setArticleResultList(List<ArticleResult> articleResultList) {
        this.articleResultList = articleResultList;
    }


    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public ArticleResult getArticleResult() {
        return articleResult;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setArticleResult(ArticleResult articleResult) {
        this.articleResult = articleResult;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public List<Permission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<Permission> permissionList) {
        this.permissionList = permissionList;
    }
    public List<ArticleResult> getArticleResults() {
        return articleResults;
    }

    public void setArticleResults(List<ArticleResult> articleResults) {
        this.articleResults = articleResults;
    }

    public Article getLast() {
        return last;
    }

    public void setLast(Article last) {
        this.last = last;
    }

    public Article getNext() {
        return next;
    }

    public void setNext(Article next) {
        this.next = next;
    }

    public List<CommentResult> getCommentResultList() {
        return commentResultList;
    }

    public void setCommentResultList(List<CommentResult> commentResultList) {
        this.commentResultList = commentResultList;
    }
    public void setConnum(Integer connum){
        this.connum = connum;
    }
    public Integer getConnum(){
        return  connum;
    }

    public Message getMesa() {
        return mesa;
    }

    public void setMesa(Message mesa) {
        this.mesa = mesa;
    }
}
