package com.gdgm.blog.mapper;


import com.gdgm.blog.bean.Article;
import com.gdgm.blog.common.result.ArticleResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleMapper {
     void saveArticle(Article article);

    List<Article> queryArticles(@Param("data") Integer data, @Param("startIndex") Integer startIndex, @Param("pagesize") Integer pagesize, @Param("memberid") Integer memberid);

    Integer queryAllArticle(@Param("data") Integer data, @Param("memberid") Integer memberid);

    Article queryArticleById(Integer id);

    void updateArticleById(Article article);

    int updateStatusById(@Param("id") Integer id, @Param("status") String status);

    Integer queryArticleByStatus(@Param("status") String status, @Param("id") Integer id);

    Integer deleteArticle(Integer id);

    List<Article> queryAdminArticles(@Param("data") Integer data, @Param("startIndex") Integer startIndex, @Param("pagesize") Integer pagesize);

    Integer queryAdminAllArticle(@Param("data") Integer data);

    Integer queryAllCount();

    Integer queryAllCountByMemberid(Integer id);

    List<Article> queryPage(@Param("pageno") Integer pageno, @Param("pagesize")Integer pagesize);

    List<Article> queryPageByType(@Param("startIndex")Integer startIndex, @Param("pagesize")Integer pagesize,@Param("id") Integer id);

    List<Article> queryArticleByAccess();

    List<Article> queryPageByLabel(@Param("startIndex")Integer startIndex, @Param("pagesize")Integer pagesize, @Param("id")Integer id);

    List<ArticleResult> queryArticleByLike();

    List<Article> queryPageByMember(@Param("startIndex")Integer startIndex, @Param("pagesize")Integer pagesize, @Param("id")Integer id);

    List<Article> queryArticlesByGreaterThanId(@Param("id") Integer id,@Param("typeid")Integer typeid);

    List<Article> queryArticlesByLessThanId(@Param("id")Integer id,@Param("typeid")Integer typeid);

    List<Article> queryFiveArticle(@Param("articleid") Integer articleid,@Param("typeid") Integer typeid);

    Integer updateComnumByArticleid(Integer articleid);

    Integer updateReduceComnumByArticleid(@Param("articleid") Integer articleid,@Param("count") Integer count);

    Integer updateLikeById(@Param("id")Integer id, @Param("num")Integer num);

    Integer updateFabulousArticleById(@Param("id")Integer id, @Param("num")Integer num);

    Integer updateAccessById(Integer id);

    Integer queryCountByKeyCoke(String keycode);

    List<ArticleResult> queryPageByKeyCoke(@Param("startIndex") Integer startIndex, @Param("pagesize") Integer pagesize, @Param("keycode") String keycode);

    List<Integer> queryIdsByMemberid(Integer id);

    void deleteArticleByMemberId(Integer id);
}
