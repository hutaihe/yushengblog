package com.gdgm.blog.service.impl;


import com.gdgm.blog.bean.Picture;
import com.gdgm.blog.mapper.PictureMapper;
import com.gdgm.blog.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PictureServiceImpl implements PictureService {
    @Autowired
    private PictureMapper pictureMapper;

    @Override
    public Integer deletePictureById(Integer id) {
        return pictureMapper.deletePictureById(id);
    }

    @Override
    public Integer queryAllCountByMemberid(Integer id) {
        return pictureMapper.queryAllCountByMemberid(id);
    }

    @Override
    public Integer queryAllCount() {
        return pictureMapper.queryAllCount();
    }

    @Override
    public int savePicture(Picture picture) {
        return pictureMapper.savePicture(picture);
    }

    @Override
    public List<Picture> queryAllPicture() {
        return pictureMapper.queryAllPicture();
    }

    @Override
    public Picture queryPictureById(Integer pid) {
        return pictureMapper.queryPictureById(pid);
    }

    @Override
    public List<Picture> queryAllPictureByIconpath(String iconpath) {
        return pictureMapper.queryAllPictureByIconpath(iconpath);
    }

    @Override
    public Integer selectOnePicture() {
        return pictureMapper.selectOnePicture();
    }
}
