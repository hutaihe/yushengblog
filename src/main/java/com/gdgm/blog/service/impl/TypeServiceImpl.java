package com.gdgm.blog.service.impl;


import com.gdgm.blog.bean.Type;
import com.gdgm.blog.mapper.TypeMapper;
import com.gdgm.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypeMapper typeMapper;

    @Override
    public List<Type> queryAllType() {
        return typeMapper.queryAllType();
    }

    @Override
    public void saveType(Integer pid, String typename) {
        typeMapper.saveType(pid,typename);
    }

    @Override
    public  Type queryTypeById(Integer id) {
        return typeMapper.queryTypeById(id);
    }

    @Override
    public Integer updateTypeById(Type type) {
       return typeMapper.updateTypeById(type);
    }

    @Override
    public Type queryTypeByName(String typename) {
        return typeMapper.queryTypeByName(typename);
    }

    @Override
    public Integer deleteTypeById(Integer id) {
        return typeMapper.deleteTypeById(id);
    }

    @Override
    public List<Type> queryTyepByPid(Integer id) {
        return typeMapper.queryTyepByPid(id);
    }

}
