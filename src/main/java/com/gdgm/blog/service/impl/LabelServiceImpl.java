package com.gdgm.blog.service.impl;


import com.gdgm.blog.bean.Label;
import com.gdgm.blog.mapper.LabelMapper;
import com.gdgm.blog.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabelServiceImpl implements LabelService
{
    @Autowired
    private LabelMapper labelMapper;

    @Override
    public List<Label> queryAllLabel() {
        return labelMapper.queryAllLabel();
    }

    @Override
    public Label queryLabelByName(String labelname) {
        return labelMapper.queryLabelByName(labelname);
    }

    @Override
    public Integer saveLabel(String labelname) {
        return labelMapper.saveLabel(labelname);
    }

    @Override
    public Label queryLabelById(Integer id) {
        return labelMapper.queryLabelById(id);
    }

    @Override
    public void updateLabelById(Label label) {
        labelMapper.updateLabelById(label);
    }

    @Override
    public void deleteLabelById(Label label) {
        labelMapper.deleteLabelById(label);
    }
}
