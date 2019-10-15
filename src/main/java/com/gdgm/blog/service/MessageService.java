package com.gdgm.blog.service;

import com.gdgm.blog.bean.Message;
import com.gdgm.blog.common.util.Page;

import java.util.Map;

public interface MessageService {
    Page queryPage(Map<String, Object> map);

    Message queryMessageByMessageid(Integer id);

    Integer updateMessageById(Integer id,String reply);

    Integer deleteMessageById(Integer id);

    void deleteMessageByMemberid(Integer id);

    Page loadMessage(Map<String, Object> map);

    void saveMessage(Message message);
}
