package com.gdgm.blog.common.vo;


import com.gdgm.blog.bean.Type;

import java.util.List;

public class TypeVo {
    private Type type;
    private List<Type> typeList;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Type> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<Type> typeList) {
        this.typeList = typeList;
    }
}
