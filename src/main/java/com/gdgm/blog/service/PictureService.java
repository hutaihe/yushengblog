package com.gdgm.blog.service;


import com.gdgm.blog.bean.Picture;

import java.util.List;

public interface PictureService {
    int savePicture(Picture picture);

    List<Picture> queryAllPicture();

    Picture queryPictureById(Integer pid);

    List<Picture> queryAllPictureByIconpath(String iconpath);

    Integer deletePictureById(Integer id);

    Integer queryAllCount();

    Integer queryAllCountByMemberid(Integer id);
}
