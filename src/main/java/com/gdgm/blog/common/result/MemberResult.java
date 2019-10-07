package com.gdgm.blog.common.result;

public class MemberResult {
    private Integer id;
    private String role;
    private String loginacct;
    private String email;
    private String username;
    private String imagePath; //图片名称
    private String personalnote; //个人说明

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLoginacct() {
        return loginacct;
    }

    public void setLoginacct(String loginacct) {
        this.loginacct = loginacct;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getPersonalnote() {
        return personalnote;
    }

    public void setPersonalnote(String personalnote) {
        this.personalnote = personalnote;
    }
}
