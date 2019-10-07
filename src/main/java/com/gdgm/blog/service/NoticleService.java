package com.gdgm.blog.service;


import com.gdgm.blog.bean.Noticle;
import com.gdgm.blog.common.util.Page;

public interface NoticleService {
    Page queryPage(Integer pageno, Integer pagesize,String status);

    int saveNoticle(Noticle noticle);

    int updateStatus(Noticle noticle);

    Noticle queryNoticleById(Integer id);

    Integer updateNoticleById(Noticle noticle);

    Integer deleteNoticleById(Integer id);
}
