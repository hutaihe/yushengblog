package com.gdgm.blog.service;

import com.gdgm.blog.bean.ArticleLabel;
import com.gdgm.blog.bean.Label;

import java.util.List;

public interface ArticleLabelService {
    int saveArticleLabe(int article1id, Integer labelid);

    List<Label> queryLabelByArticleId(Integer id);

    List<Integer> queryLabelIdByArticleId(Integer id);

    void deletearticleLabel(Integer id);

    List<ArticleLabel> queryAllArticleLabel();

    void deletearticleLabelByLabelid(Integer id);

    void deleteArticleLabelByArticleIds(List<Integer> ids);
}
