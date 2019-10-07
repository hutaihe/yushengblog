package com.gdgm.blog.service.impl;


import com.gdgm.blog.bean.ArticleLabel;
import com.gdgm.blog.bean.Label;
import com.gdgm.blog.mapper.ArticleLabelMapper;
import com.gdgm.blog.service.ArticleLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleLabelServiceImpl implements ArticleLabelService {

    @Autowired
    private ArticleLabelMapper articleLabelMapper;

    @Override
    public int saveArticleLabe(int article1id, Integer labelid) {
        return articleLabelMapper.saveArticleLabe(article1id,labelid);
    }

    @Override
    public List<Label> queryLabelByArticleId(Integer id) {
        return articleLabelMapper.queryLabelByArticleId(id);
    }

    @Override
    public List<Integer> queryLabelIdByArticleId(Integer id) {
        return articleLabelMapper.queryLabelIdByArticleId(id);
    }

    @Override
    public void deletearticleLabel(Integer id) {
        articleLabelMapper.deletearticleLabel(id);
    }

    @Override
    public List<ArticleLabel> queryAllArticleLabel() {
        return articleLabelMapper.queryAllArticleLabel();
    }

    @Override
    public void deletearticleLabelByLabelid(Integer id) {
        articleLabelMapper.deletearticleLabelByLabelid(id);
    }

}
