package com.gdgm.blog.common.result;

public class DataResult {
    private Integer articles; //文章数
    private Integer comments; //评论数
    private Integer enclosures; //附件数
    private Integer days; //成立天数
    private String createtime ;

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public Integer getArticles() {
        return articles;
    }

    public void setArticles(Integer articles) {
        this.articles = articles;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Integer getEnclosures() {
        return enclosures;
    }

    public void setEnclosures(Integer enclosures) {
        this.enclosures = enclosures;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }
}
