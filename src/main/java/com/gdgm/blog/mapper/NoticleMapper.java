package com.gdgm.blog.mapper;


import com.gdgm.blog.bean.Noticle;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NoticleMapper {

    Integer queryCount(String status);

    List<Noticle> queryPageNoticle(@Param("startIndex") Integer startIndex, @Param("pagesize") Integer pagesize,@Param("status") String status);

    int saveNoticle(Noticle noticle);

    int updateStatus(Noticle noticle);

    Noticle queryNoticleById(Integer id);

    Integer updateNoticleById(Noticle noticle);

    Integer deleteNoticleById(Integer id);
}
