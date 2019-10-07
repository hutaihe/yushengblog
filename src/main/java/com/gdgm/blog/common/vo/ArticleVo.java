package com.gdgm.blog.common.vo;

import com.gdgm.blog.bean.Type;

import java.util.List;

/**
 * 接受编写文章的数据
 */
public class ArticleVo {
    private Integer id;

    private String title;

    private String content ;

    private Integer pid;

    private String evaluate;

    private String type; //文章类型

    private String status;

    private List<Integer> labelids ;

    private List<Integer> typeids;

    private Type classification;//文章分类

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

    public List<Integer> getLabelids() {
        return labelids;
    }

    public void setLabelids(List<Integer> labelids) {
        this.labelids = labelids;
    }

    public List<Integer> getTypeids() {
        return typeids;
    }

    public void setTypeids(List<Integer> typeids) {
        this.typeids = typeids;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Type getClassification() {
        return classification;
    }

    public void setClassification(Type classification) {
        this.classification = classification;
    }
}
