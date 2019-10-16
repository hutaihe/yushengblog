package com.gdgm.blog.service.impl;


import com.gdgm.blog.bean.*;
import com.gdgm.blog.common.result.ArticleResult;
import com.gdgm.blog.common.util.Page;
import com.gdgm.blog.common.vo.ArticleVo;
import com.gdgm.blog.mapper.ArticleMapper;
import com.gdgm.blog.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;


    @Autowired
    private ArticleTypeService articleTypeService;
    @Autowired
    private ArticleLabelService articleLabelService;
    @Autowired
    private PictureService pictureService;
    @Autowired
    private  MemberService memberService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private LabelService labelService;
    @Override
    public Integer queryAllCount() {
        return articleMapper.queryAllCount();
    }

    @Override
    public Integer queryAllCountByMemberid(Integer id) {
        return articleMapper.queryAllCountByMemberid(id);
    }

    @Autowired
    private RoleService roleService;
    //分页查询数据
    @Override
    public Page queryPageArticle(Member member, Integer pageno, Integer pagesize, Integer data) {
        List<ArticleResult> articleResults = new ArrayList<ArticleResult>();
        Page page = new Page(pageno,pagesize);
        Integer startIndex = page.getStartIndex(); //分页的开始位置
        //查询当前账号的角色
        Role role = roleService.queryRoleByMemberid(member.getId());
        List<Article>  articleList =null;//文章
        Integer totalsize=null;//文章的篇数
        //如果是管理员就查询所有的文章
       if(role.getName().equals("管理员")){
           //文章信息
           articleList = articleMapper.queryAdminArticles(data,startIndex,pagesize);
           totalsize = articleMapper.queryAdminAllArticle(data);
       }else if(role.getName().equals("投稿者")){//如果是投稿者就查询投稿者的文章
           //文章信息
           articleList = articleMapper.queryArticles(data,startIndex,pagesize,member.getId());
           totalsize = articleMapper.queryAllArticle(data,member.getId());
        }


        page.setTotalsize(totalsize);
        for(Article article : articleList){
            ArticleResult articleResult = new ArticleResult();
            articleResult.setId(article.getId()); //文章id
            articleResult.setAccess(article.getAccess()); //访问量
            articleResult.setCreationtime(article.getCreationtime());//创建时间
            articleResult.setTitle(article.getTitle()); //文章标题
            articleResult.setComnum(article.getComnum()); //文章评论
            //查询作者
            String username = memberService.queryMemberByArticleId(article.getMemberid());
            articleResult.setUsername(username); //作者
            List<Type> typeList = articleTypeService.queryTypeByArticleId(article.getId());

            StringBuilder typenames = new StringBuilder(); //分类目录
            for(Type type : typeList){
                typenames.append(type.getName()+" ");
            }
            String typenames1 = typenames.toString();
            articleResult.setTypenames(typenames1);
            List<Label> labelList =articleLabelService .queryLabelByArticleId(article.getId());
            StringBuilder labelnames = new StringBuilder(); //标签
            for(Label label : labelList){
                labelnames.append(label.getName()+" ");
            }
            String labelnames1 = labelnames.toString();
            articleResult.setLabelnames(labelnames1);

            articleResults.add(articleResult);
            page.setArticleResults(articleResults);
        }
        return page;
    }
    //查询文章
    @Override
    public Article queryArticleById(Integer id) {
        return articleMapper.queryArticleById(id);
    }
    //修改文章
    @Override
    public boolean updateArticle(ArticleVo articleVo, Integer id) {
        Article article = new Article();
        article.setId(articleVo.getId());
        article.setMemberid(id);
        article.setTitle(articleVo.getTitle());
        article.setContent(articleVo.getContent());
        article.setType(articleVo.getType()); //文章类型
        article.setEvaluate(articleVo.getEvaluate()); //是否评论
        article.setStatus(articleVo.getStatus());
        article.setPid(articleVo.getPid());
        article.setAccess(0);
        articleMapper.updateArticleById(article);
        int total1=0;
        //如果没有选择分类默认未分类
        if(articleVo.getTypeids() == null){
            List<Integer> typeids = new ArrayList<>();
            typeids.add(63);
            articleVo.setTypeids(typeids);
        }
        articleTypeService.deleteArticleType(article.getId());
        for(Integer typeid : articleVo.getTypeids()){
            int count =  articleTypeService.saveArticleType(article.getId(),typeid);
            total1 += count;
        }
        int total2=0;
        //如果没有选择标签默认未没标签
        if(articleVo.getLabelids() == null){
            List<Integer> labelids = new ArrayList<>();
            Label label = labelService.queryLabelByName("其他");
            labelids.add(label.getId());
            articleVo.setLabelids(labelids);
        }
        articleLabelService.deletearticleLabel(article.getId());
        for (Integer labelid : articleVo.getLabelids()){
            int count = articleLabelService.saveArticleLabe(article.getId(),labelid);
            total2 += count;
        }
        return true;
    }
    //根据articleid修改文章状态
    @Override
    public int updateStatusById(Integer id,String status) {
        return articleMapper.updateStatusById(id,status);
    }

    @Override
    public Integer queryArticleByStatus(String status, Integer id) {
        return articleMapper.queryArticleByStatus(status,id);
    }
    //根据文章id删除文章
    @Override
    public Integer deleteArticle(Integer id) {
        //删除分类关联
        articleTypeService.deleteArticleType(id);
        //删除标签
        articleLabelService.deletearticleLabel(id);
        //删除文章
        Integer count = articleMapper.deleteArticle(id);
        return count;
    }



    //保存文章
    @Override
    public boolean saveArticle(ArticleVo articleVo,Integer memberid) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm");
        String date = simpleDateFormat.format(new Date());
        //保存文章信息
        Article article = new Article();
        article.setMemberid(memberid);
        article.setTitle(articleVo.getTitle());
        article.setContent(articleVo.getContent());
        article.setType(articleVo.getType()); //文章类型
        article.setEvaluate(articleVo.getEvaluate()); //是否评论
        article.setStatus(articleVo.getStatus());
        //如果没有选则缩略图，就取数据库中的第一张图片
        if(articleVo.getPid() == null){
            Integer id = pictureService.selectOnePicture();
            articleVo.setPid(id);
        }
        article.setPid(articleVo.getPid());
        article.setAccess(0);
        article.setCreationtime(date);
        articleMapper.saveArticle(article);
        //保存文章分类
        int total1=0;
        //如果没有选择分类默认是其他
        if(articleVo.getTypeids() == null){
            List<Integer> typeids = new ArrayList<>();
            Type type = typeService.queryTypeByName("其他");
            typeids.add(type.getId());
            articleVo.setTypeids(typeids);
        }
        for(Integer typeid : articleVo.getTypeids()){
           int count =  articleTypeService.saveArticleType(article.getId(),typeid);
            total1 += count;
        }
        //保存文章标签
        int total2=0;
        //如果没有选择标签默认未没标签
        if(articleVo.getLabelids() == null){
            List<Integer> labelids = new ArrayList<>();
            labelids.add(39);
            articleVo.setLabelids(labelids);

        }
        for (Integer labelid : articleVo.getLabelids()){
            int count = articleLabelService.saveArticleLabe(article.getId(),labelid);
            total2 += count;
        }
        //保存文章和用户的关系表
       // int count = memberArticleMapper.saveMemberArticle(memberid,article.getId());
        if(total1 == articleVo.getTypeids().size() && total2 == articleVo.getLabelids().size() ){
            return true;
        }
        return false;
    }



    @Override
    public Page queryPage(Integer pageno, Integer pagesize) {
        Page page = new Page(pageno,pagesize);
        Integer totalsize = articleMapper.queryAllCount();
        page.setTotalsize(totalsize);
        List<Article> articles = articleMapper.queryPage(page.getStartIndex(),pagesize);
        List<ArticleResult> articleResults = new ArrayList<>();
        for(Article article : articles){
            ArticleResult articleResult = new ArticleResult() ;
            //将article对象的属性复制到articleResult中去
            BeanUtils.copyProperties(article,articleResult);
            //查询图片
            Picture picture = pictureService.queryPictureById(article.getPid());
            List<Type> types = articleTypeService.queryTypeByArticleId(article.getId());
            //如果分类有两条，什么有子分类，那就选择子分类
            if(types.size()>=2){
                for(Type type : types){
                    if(type.getPid()!= null){
                        articleResult.setClassification(type);
                    }
                }
            }else{
                articleResult.setClassification(types.get(0));
            }
           //存储图片
            articleResult.setPicture(picture);
            //控制conten的字数
            String content = article.getContent().replaceAll("<.*?>", " ").replaceAll("", "");
            content =  content.replaceAll("<.*?", "");
            //如果文字长度超过200个字符则切割字符
            if(content.length()>=200){
                content = content.substring(0,200)+"...";
            }
            //查询作者
            Member member = memberService.queryMemberById(article.getMemberid());
            articleResult.setUsername(member.getUsername());
            articleResult.setContent(content);
            articleResults.add(articleResult);
        }
        page.setArticleResults(articleResults);
        return page;
    }
    //根据分类查询文章
    @Override
    public Page queryPageByType(Integer pageno, Integer pagesize, Integer id) {
        Page page = new Page(pageno,pagesize);
        Integer totalsize = articleMapper.queryAllCount();
        page.setTotalsize(totalsize);
        List<Article> articles = articleMapper.queryPageByType(page.getStartIndex(),pagesize,id);
        List<ArticleResult> articleResults = new ArrayList<>();
        for(Article article : articles){
            ArticleResult articleResult = new ArticleResult() ;
            //将article对象的属性复制到articleResult中去
            BeanUtils.copyProperties(article,articleResult);
            //查询图片
            Picture picture = pictureService.queryPictureById(article.getPid());
            List<Type> types = articleTypeService.queryTypeByArticleId(article.getId());
            //如果分类有两条，什么有子分类，那就选择子分类
            if(types.size()>=2){
                for(Type type : types){
                    if(type.getPid()!= null){
                        articleResult.setClassification(type);
                    }
                }
            }else{
                articleResult.setClassification(types.get(0));
            }
            //存储图片
            articleResult.setPicture(picture);
            //控制conten的字数
            String content = article.getContent().replaceAll("<.*?>", " ").replaceAll("", "");
            content =  content.replaceAll("<.*?", "");
            //如果文字长度超过200个字符则切割字符
            if(content.length()>=200){
                content = content.substring(0,200)+"...";
            }
            //查询作者
            Member member = memberService.queryMemberById(article.getMemberid());
            articleResult.setUsername(member.getUsername());
            articleResult.setContent(content);
            articleResults.add(articleResult);
        }
        page.setArticleResults(articleResults);
        return page;
    }
    @Override
    public Page queryPageByLabel(Integer pageno, Integer pagesize, Integer id) {
        Page page = new Page(pageno,pagesize);
        Integer totalsize = articleMapper.queryAllCount();
        page.setTotalsize(totalsize);
        List<Article> articles = articleMapper.queryPageByLabel(page.getStartIndex(),pagesize,id);
        List<ArticleResult> articleResults = new ArrayList<>();
        for(Article article : articles){
            ArticleResult articleResult = new ArticleResult() ;
            //将article对象的属性复制到articleResult中去
            BeanUtils.copyProperties(article,articleResult);
            //查询图片
            Picture picture = pictureService.queryPictureById(article.getPid());
            List<Type> types = articleTypeService.queryTypeByArticleId(article.getId());
            //如果分类有两条，什么有子分类，那就选择子分类
            if(types.size()>=2){
                for(Type type : types){
                    if(type.getPid()!= null){
                        articleResult.setClassification(type);
                    }
                }
            }else{
                articleResult.setClassification(types.get(0));
            }
            //存储图片
            articleResult.setPicture(picture);
            //控制conten的字数
            String content = article.getContent().replaceAll("<.*?>", " ").replaceAll("", "");
            content =  content.replaceAll("<.*?", "");
            //如果文字长度超过200个字符则切割字符
            if(content.length()>=200){
                content = content.substring(0,200)+"...";
            }
            //查询作者
            Member member = memberService.queryMemberById(article.getMemberid());
            articleResult.setUsername(member.getUsername());
            articleResult.setContent(content);
            articleResults.add(articleResult);
        }
        page.setArticleResults(articleResults);
        return page;
    }
    @Override
    public List<Article> queryArticleByAccess() {
        return articleMapper.queryArticleByAccess();
    }
    @Override
    public List<ArticleResult> queryArticleByLike() {
        List<ArticleResult> articleResults = articleMapper.queryArticleByLike();
        for(ArticleResult articleResult : articleResults){
            Picture picture = pictureService.queryPictureById(articleResult.getPid());
            if(articleResult.getTitle().length()>25){
                articleResult.setTitle(articleResult.getTitle().substring(0,25)+"...");
            }
            articleResult.setCreationtime(articleResult.getCreationtime().substring(0,10));
            articleResult.setPicture(picture);
        }
        return articleResults;
    }
    @Override
    public ArticleResult queryArticleContentById(Integer id) {
        ArticleResult articleResult = new ArticleResult();
        Article article = articleMapper.queryArticleById(id); //根据文章id查询文章
        List<Type> types = articleTypeService.queryTypeByArticleId(id); //查询分类
        List<Label> labels = articleLabelService.queryLabelByArticleId(id); //查询标签
        Member member = memberService.queryMemberById(article.getMemberid()); //查询作者
        BeanUtils.copyProperties(article,articleResult);
        articleResult.setUsername(member.getUsername());
        articleResult.setTypes(types);
        articleResult.setLabels(labels);
        return articleResult;
    }
    @Override
    public Page queryPageMember(Integer pageno, Integer pagesize, Integer id) {
        Page page = new Page(pageno,pagesize);
        Integer totalsize = articleMapper.queryAllCount();
        page.setTotalsize(totalsize);
        List<Article> articles = articleMapper.queryPageByMember(page.getStartIndex(),pagesize,id);
        List<ArticleResult> articleResults = new ArrayList<>();
        for(Article article : articles){
            ArticleResult articleResult = new ArticleResult() ;
            //将article对象的属性复制到articleResult中去
            BeanUtils.copyProperties(article,articleResult);
            //查询图片
            Picture picture = pictureService.queryPictureById(article.getPid());
            List<Type> types = articleTypeService.queryTypeByArticleId(article.getId());
            //如果分类有两条，什么有子分类，那就选择子分类
            if(types.size()>=2){
                for(Type type : types){
                    if(type.getPid()!= null){
                        articleResult.setClassification(type);
                    }
                }
            }else{
                articleResult.setClassification(types.get(0));
            }
            //存储图片
            articleResult.setPicture(picture);
            //控制conten的字数
            String content = article.getContent().replaceAll("<.*?>", " ").replaceAll("", "");
            content =  content.replaceAll("<.*?", "");
            //如果文字长度超过200个字符则切割字符
            if(content.length()>=200){
                content = content.substring(0,200)+"...";
            }
            //查询作者
            Member member = memberService.queryMemberById(article.getMemberid());
            articleResult.setUsername(member.getUsername());
            articleResult.setContent(content);
            articleResults.add(articleResult);
        }
        page.setArticleResults(articleResults);
        return page;
    }

    @Override
    public Article queryArticlesByLessThanId(Integer id,Integer typeid) {
        List<Article> articles =  articleMapper.queryArticlesByLessThanId(id,typeid);
        //如果查询的文章数大于1说明不止一条，就取最后一条
        if(articles.size()>=1 && articles != null){
            return articles.get(articles.size()-1);
        }
        return null;
    }
    @Override
    public Article queryArticlesByGreaterThanId(Integer id,Integer typeid) {
        List<Article> articles =  articleMapper.queryArticlesByGreaterThanId(id,typeid);
        //如果查询的文章数大于1说明不止一条，就取第一条
        if(articles.size()>=1 && articles != null){
            return articles.get(0);
        }
        return null;
    }

    @Override
    public List<Article> queryFiveArticle(Integer articleid, Integer typeid) {
        return articleMapper.queryFiveArticle(articleid,typeid);
    }
    @Override
    public Integer updateComnumByArticleid(Integer articleid) {
        return articleMapper.updateComnumByArticleid(articleid);
    }
    @Override
    public Integer updateReduceComnumByArticleid(Integer articleid,Integer count) {
        return articleMapper.updateReduceComnumByArticleid(articleid,count);
    }
    @Override
    public Integer updateLikeById(Integer id,Integer num) {
        return articleMapper.updateLikeById(id,num);
    }

    @Override
    public Integer updateFabulousArticleById(Integer id, int num) {
        return articleMapper.updateFabulousArticleById(id,num);
    }

    @Override
    public Integer updateAccessById(Integer id) {
        return articleMapper.updateAccessById(id);
    }

    @Override
    public Page queryPageByKeyCode(Integer pageno, Integer pagesize, String keycode) {
        Page page = new Page(pageno,pagesize);
        Integer totalsize = articleMapper.queryCountByKeyCoke(keycode);
        page.setTotalsize(totalsize);
        List<ArticleResult> articleResults = articleMapper.queryPageByKeyCoke(page.getStartIndex(),pagesize,keycode);
        page.setArticleResults(articleResults);
        return page;
    }

    @Override
    public List<Integer> queryIdsByMemberid(Integer id) {
        return articleMapper.queryIdsByMemberid(id);
    }

    @Override
    public void deleteArticleByMemberId(Integer id) {
        articleMapper.deleteArticleByMemberId(id);
    }

    @Override
    public Page queryPageByMap(Map<String, Object> map) {
        Integer pageno = (Integer)map.get("pageno");
        Integer pagesize = (Integer)map.get("pagesize");
        Page page = new Page(pageno,pagesize);
        map.put("startIndex",page.getStartIndex());
        Integer totalsize = articleMapper.queryAllCountByMap(map);
        page.setTotalsize(totalsize);
        List<Article> articles = articleMapper.queryPageMap(map);
        List<ArticleResult> articleResults = new ArrayList<>();
        for(Article article : articles){
            ArticleResult articleResult = new ArticleResult() ;
            //将article对象的属性复制到articleResult中去
            BeanUtils.copyProperties(article,articleResult);
            //查询图片
            Picture picture = pictureService.queryPictureById(article.getPid());
            List<Type> types = articleTypeService.queryTypeByArticleId(article.getId());
            //如果分类有两条，什么有子分类，那就选择子分类
            if(types.size()>=2){
                for(Type type : types){
                    if(type.getPid()!= null){
                        articleResult.setClassification(type);
                    }
                }
            }else{
                articleResult.setClassification(types.get(0));
            }
            //存储图片
            articleResult.setPicture(picture);
            //控制conten的字数
            String content = article.getContent().replaceAll("<.*?>", " ").replaceAll("", "");
            content =  content.replaceAll("<.*?", "");
            //如果文字长度超过200个字符则切割字符
            if(content.length()>=200){
                content = content.substring(0,200)+"...";
            }
            //查询作者
            Member member = memberService.queryMemberById(article.getMemberid());
            articleResult.setUsername(member.getUsername());
            articleResult.setContent(content);
            articleResults.add(articleResult);
        }
        page.setArticleResults(articleResults);
        return page;
    }
}
