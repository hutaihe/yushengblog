package com.gdgm.blog.service.impl;


import com.gdgm.blog.bean.Noticle;
import com.gdgm.blog.common.util.Page;
import com.gdgm.blog.mapper.NoticleMapper;
import com.gdgm.blog.service.NoticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticleServiceImpl implements NoticleService {
    @Autowired
    private NoticleMapper noticleMapper;

    @Override
    public Page queryPage(Integer pageno, Integer pagesize,String status) {
        Page page = new Page(pageno,pagesize);
        Integer totalsize = noticleMapper.queryCount(status);
        page.setTotalsize(totalsize);
        List<Noticle> noticleList = noticleMapper.queryPageNoticle(page.getStartIndex(),pagesize,status);
        page.setNoticleList(noticleList);
        return page;
    }

    @Override
    public int saveNoticle(Noticle noticle) {

        return noticleMapper.saveNoticle(noticle);
    }
    @Override
    public int updateStatus(Noticle noticle) {
        return noticleMapper.updateStatus(noticle);
    }

    @Override
    public Noticle queryNoticleById(Integer id) {
        return noticleMapper.queryNoticleById(id);
    }
    @Override
    public Integer updateNoticleById(Noticle noticle) {
        return noticleMapper.updateNoticleById(noticle);
    }

    @Override
    public Integer deleteNoticleById(Integer id) {
        return noticleMapper.deleteNoticleById(id);
    }
}
