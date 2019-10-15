package com.gdgm.blog.common.result;


import com.gdgm.blog.bean.Label;
import com.gdgm.blog.bean.Picture;
import com.gdgm.blog.bean.Type;

import java.util.List;

/**
 * 返回到界面关于文章的所有数据
 */
public class MessageResult {
    private Integer id;
    private Integer memberid;
    private String content;
    private String createtime;
    private String username;
    private String reply;
    private String status;
    private String image;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
