package com.gdgm.blog.service;


import com.gdgm.blog.bean.Article;
import com.gdgm.blog.bean.Comment;
import com.gdgm.blog.bean.Member;
import com.gdgm.blog.common.result.ArticleResult;
import com.gdgm.blog.common.util.Page;
import com.gdgm.blog.common.vo.ArticleVo;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    boolean saveArticle(ArticleVo articleVo, Integer memberid);

    Page queryPageArticle(Member member, Integer pageno, Integer pagesize, Integer data);

    Article queryArticleById(Integer id);

    boolean updateArticle(ArticleVo articleVo, Integer id);

    int updateStatusById(Integer id, String status);

    Integer queryArticleByStatus(String status, Integer id);

    Integer deleteArticle(Integer id);

    Integer queryAllCount();

    Integer queryAllCountByMemberid(Integer id);

    Page queryPage(Integer pageno, Integer pagesize);

    Page queryPageByType(Integer pageno, Integer pagesize, Integer id);

    List<Article> queryArticleByAccess();

    Page queryPageByLabel(Integer pageno, Integer pagesize, Integer id);

    List<ArticleResult> queryArticleByLike();

    ArticleResult queryArticleContentById(Integer id);

    Page queryPageMember(Integer pageno, Integer pagesize, Integer id);

    Article queryArticlesByGreaterThanId(Integer id,Integer typeid);

    Article queryArticlesByLessThanId(Integer id,Integer typeid);

    List<Article> queryFiveArticle(Integer id, Integer id1);

    Integer updateComnumByArticleid(Integer articleid);

    Integer updateReduceComnumByArticleid(Integer articleid,Integer count);

    Integer updateLikeById(Integer id,Integer num);

    Integer updateFabulousArticleById(Integer id, int i);

    Integer updateAccessById(Integer id);

    Page queryPageByKeyCode(Integer pageno, Integer pagenosize, String name);

    List<Integer> queryIdsByMemberid(Integer id);

    void deleteArticleByMemberId(Integer id);

    Page queryPageByMap(Map<String, Object> map);
}
