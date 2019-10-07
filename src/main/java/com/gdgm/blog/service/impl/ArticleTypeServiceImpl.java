package com.gdgm.blog.service.impl;


import com.gdgm.blog.bean.ArticleType;
import com.gdgm.blog.bean.Type;
import com.gdgm.blog.mapper.ArticleTypeMapper;
import com.gdgm.blog.service.ArticleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleTypeServiceImpl implements ArticleTypeService {
    @Autowired
    private ArticleTypeMapper articleTypeMapper;

    @Override
    public int saveArticleType(int article1id, Integer typeid) {
        return articleTypeMapper.saveArticleType(article1id,typeid);
    }

    @Override
    public List<Type> queryTypeByArticleId(Integer id) {
        return articleTypeMapper.queryTypeByArticleId(id);
    }

    @Override
    public int deleteArticleType(Integer articleid) {
        return articleTypeMapper.deleteArticleType(articleid);
    }

    @Override
    public List<ArticleType> queryAllArticleType() {
        return articleTypeMapper.queryAllArticleType();
    }

    @Override
    public void deleteArticleTypeByTypeId(Integer id) {
        articleTypeMapper.deleteArticleTypeByTypeId(id);
    }
}
