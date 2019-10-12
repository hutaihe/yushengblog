package com.gdgm.blog.mapper;


import com.gdgm.blog.bean.ArticleLabel;
import com.gdgm.blog.bean.Label;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleLabelMapper {
    int saveArticleLabe(@Param("articleid") int article1id, @Param("labelid") Integer labelid);

    List<Label> queryLabelByArticleId(Integer id);

    List<Integer> queryLabelIdByArticleId(Integer id);

    void deletearticleLabel(@Param("articleid") Integer id);

    List<ArticleLabel> queryAllArticleLabel();

    void deletearticleLabelByLabelid(Integer id);

    void deleteArticleLabelByArticleIds(List<Integer> ids);
}
