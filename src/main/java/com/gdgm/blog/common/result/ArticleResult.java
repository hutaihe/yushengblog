package com.gdgm.blog.common.result;


import com.gdgm.blog.bean.Label;
import com.gdgm.blog.bean.Picture;
import com.gdgm.blog.bean.Type;

import java.util.List;

/**
 * 返回到界面关于文章的所有数据
 */
public class ArticleResult {
    private Integer id;
    private Integer memberid; //作者id
    private String title; //标题
    private String content ; //内容
    private Integer pid; //缩略图
    private String evaluate;//是否开启评论
    private String type; //文章类型
    private String status; //状态
    private Integer access; //访问量
    private String creationtime; //创建时间
    private String labelnames;//标签P
    private String typenames; //分类
    private String username; //作者
    private List<Type> types; //分类的id集合
    private List<Label> labels; // 标签的id集合
    private Picture picture;
    private Type classification; //文章分类
    private Integer likenum; //喜欢数
    private Integer comnum; //评论数
    private Integer fabulous;//赞
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getMemberid() {
        return memberid;
    }

    public void setMemberid(Integer memberid) {
        this.memberid = memberid;
    }

    public Integer getAccess() {
        return access;
    }

    public void setAccess(Integer access) {
        this.access = access;
    }

    public String getCreationtime() {
        return creationtime;
    }

    public void setCreationtime(String creationtime) {
        this.creationtime = creationtime;
    }

    public String getLabelnames() {
        return labelnames;
    }

    public void setLabelnames(String labelnames) {
        this.labelnames = labelnames;
    }

    public String getTypenames() {
        return typenames;
    }

    public void setTypenames(String typenames) {
        this.typenames = typenames;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public Type getClassification() {
        return classification;
    }

    public void setClassification(Type classification) {
        this.classification = classification;
    }

    public Integer getLikenum() {
        return likenum;
    }

    public void setLikenum(Integer likenum) {
        this.likenum = likenum;
    }

    public Integer getComnum() {
        return comnum;
    }

    public void setComnum(Integer comnum) {
        this.comnum = comnum;
    }

    public Integer getFabulous() {
        return fabulous;
    }

    public void setFabulous(Integer fabulous) {
        this.fabulous = fabulous;
    }
}
