package com.gdgm.blog.service.impl;


import com.gdgm.blog.bean.Member;
import com.gdgm.blog.common.result.CommentResult;
import com.gdgm.blog.common.util.Page;
import com.gdgm.blog.mapper.MemberMapper;
import com.gdgm.blog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class MemberServiceImpl implements MemberService {


    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleLabelService articleLabelService;
    @Autowired
    private ArticleTypeService articleTypeService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private MemberRoleService memberRoleService;
    @Autowired
    private MessageService messageService;
    @Override
    public Member queryMemberById(Integer id) {
        return memberMapper.queryMemberById(id);
    }

    @Override
    public Member queryMemberByLoginacct(String loginacct) {
        return memberMapper.queryMemberByLoginacct(loginacct);
    }

    @Override
    public Member queryMemberByIdAndPassword(Integer memberid, String originalpassword) {
        return memberMapper.queryMemberByIdAndPassword(memberid,originalpassword);
    }

    @Override
    public Integer updatePassword(Integer memberid, String newpassword) {
        return memberMapper.updatePassword(memberid,newpassword);
    }

    @Override
    public Member loginMember(Member member) {
        return memberMapper.loginMember(member);
    }

    @Override
    public Integer updateMember(Member member) {
        return memberMapper.updateMember(member);
    }
    @Override
    public Member queryMemberByUserName(String username) {
        return memberMapper.queryMemberByUserName(username);
    }

    @Override
    public void saveMember(Member member) {
        memberMapper.saveMember(member);
    }

    @Override
    public void updatePasswordByloginacct(String loginacct, String code) {
        memberMapper.updatePasswordByloginacct(loginacct,code);
    }

    @Override
    public Member queryMemberByEmail(String email) {
        return memberMapper.queryMemberByEmail(email);
    }

    @Override
    public List<Member> queryMembers(List<Integer> ids) {
        return memberMapper.queryMembers(ids);
    }

    @Override
    public Page queryPage(Map<String, Object> map) {
        Integer pageno = (Integer)map.get("pageno");
        Integer pagesize = (Integer)map.get("pagesize");
        String keycode = (String)map.get("keycode");
        Page page = new Page(pageno,pagesize);
        Integer startIndex = page.getStartIndex();
        map.put("startIndex",startIndex);
        Integer setTotalsize = memberMapper.queryCountByMap(map);
        page.setTotalsize(setTotalsize);
        List<Member> members = memberMapper.queryMemberByMap(map);
        page.setMemberList(members);
        return page;
    }

    @Override
    public Integer updateMemberByMemberId(Member member) {
        return memberMapper.updateMemberByMemberId(member);
    }
    @Override
    public Boolean deleteMemberById(Integer id) {
        Boolean bool = true;
            //删除该用户所有文章
            //查询该用户所有文章的id
            List<Integer> ids = articleService.queryIdsByMemberid(id);

            if(ids.size() != 0) {
                //根据文章id删除 articlelabel
                articleLabelService.deleteArticleLabelByArticleIds(ids);
                //根据文章id删除  articleType
                articleTypeService.deleteArticleTypeByArticleIds(ids);
                //删除文章的评论
                //先根据文章id删除所有评论，在删除评论其他人的
                commentService.deleteCommentByArticleIds(ids);
                List<Integer> cids = commentService.quyerCommentByMemberid(id);
                for (Integer cid : cids) {
                    CommentResult commentResult = commentService.quyerCommentById(cid);
                    //如果pid为空，就删除与之相关的所有评论
                    if(commentResult != null){
                        if (commentResult.getPid() == null) {
                            Integer count = commentService.queryCountByPpid(commentResult.getPpid());
                            commentService.deleteCommentByPpid(commentResult.getPpid());
                        } else {
                            //先删除再遍历找出相关联的评论
                            commentService.deleteComment(commentResult.getId());
                            //根据ppid和文章id查询评论
                            List<CommentResult> commentResults = commentService.quyerCommentDetail(commentResult.getPpid(), commentResult.getArticleid());
                            Map<Integer, CommentResult> map = new HashMap<Integer, CommentResult>();
                            //将查询所得的评论放到map中去
                            for (CommentResult comment : commentResults) {
                                map.put(comment.getId(), comment);
                            }
                            for (CommentResult commentResul : commentResults) {
                                if (commentResul.getPid() != null) {
                                    //这个是为了找出已经被删除的评论以及和他相关的评论
                                    CommentResult parent = map.get(commentResul.getPid());
                                    //如果为空，说明相关联的评论已经被删除了
                                    if (parent == null) {
                                        commentService.deleteComment(commentResul.getId());
                                        articleService.updateReduceComnumByArticleid(commentResul.getArticleid(), 1);
                                        //将父评论从map中移除
                                        map.remove(commentResul.getId());
                                    }
                                }
                            }
                        }
                    }
                    //删除文章
                    articleService.deleteArticleByMemberId(id);
                }
            }
            //删除留言
            messageService.deleteMessageByMemberid(id);
            //删除该用户角色
            memberRoleService.deleteMemberRoleByMemberid(id);
            //删除用户
            memberMapper.deleteMemberById(id);


        return bool;
    }

    @Override
    public String queryMemberByArticleId(Integer memberid) {
       return  memberMapper.queryMemberByArticleId(memberid);
    }
}
