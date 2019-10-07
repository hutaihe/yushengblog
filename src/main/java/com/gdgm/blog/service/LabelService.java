package com.gdgm.blog.service;


import com.gdgm.blog.bean.Label;

import java.util.List;

public interface LabelService {
    List<Label> queryAllLabel();

    Label queryLabelByName(String labelname);

    Integer saveLabel(String labelname);

    Label queryLabelById(Integer id);

    void updateLabelById(Label label);

    void deleteLabelById(Label label);
}
