package com.gdgm.blog.common.result;


import com.gdgm.blog.bean.Type;

import java.util.ArrayList;
import java.util.List;

public class TypeResult {
    private Integer id;
    private Integer pid;
    private String name;
    private List<Type> typeList = new ArrayList<Type>();
    private Integer count ; //文章数

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Type> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<Type> typeList) {
        this.typeList = typeList;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
