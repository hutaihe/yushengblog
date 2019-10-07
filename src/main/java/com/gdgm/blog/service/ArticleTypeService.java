package com.gdgm.blog.service;



import com.gdgm.blog.bean.ArticleType;
import com.gdgm.blog.bean.Type;

import java.util.List;

public interface ArticleTypeService {
    int saveArticleType(int article1id, Integer typeid);

    List<Type> queryTypeByArticleId(Integer id);

    int deleteArticleType(Integer id);

    List<ArticleType> queryAllArticleType();

    void deleteArticleTypeByTypeId(Integer id);
}
