package com.gdgm.blog.service;


import com.gdgm.blog.bean.Type;

import java.util.List;

public interface TypeService {
    List<Type> queryAllType();

    void saveType(Integer pid, String typename);


    Type  queryTypeById(Integer id);

    Integer updateTypeById(Type type);

    Type queryTypeByName(String typename);

    Integer deleteTypeById(Integer id);

    List<Type> queryTyepByPid(Integer id);
}
