package com.gdgm.blog.mapper;


import com.gdgm.blog.bean.ArticleType;
import com.gdgm.blog.bean.Type;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleTypeMapper {
    int saveArticleType(@Param("articleid") int articleid, @Param("typeid") Integer typeid);

    List<Type> queryTypeByArticleId(Integer id);

    int deleteArticleType(Integer articleid);

    List<ArticleType> queryAllArticleType();

    void deleteArticleTypeByTypeId(Integer id);
}
