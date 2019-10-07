package com.gdgm.blog.mapper;


import com.gdgm.blog.bean.Type;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TypeMapper {
    List<Type> queryAllType();

    void saveType(@Param("pid") Integer pid, @Param("typename") String typename);

    void updateType(Integer id);

    Type queryTypeById(Integer id);

    Integer updateTypeById(Type type);

    Type queryTypeByName(String typename);

    Integer deleteTypeById(Integer id);

    List<Type> queryTyepByPid(Integer id);
}
