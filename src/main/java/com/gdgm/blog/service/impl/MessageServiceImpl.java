package com.gdgm.blog.service.impl;

import com.gdgm.blog.bean.Member;
import com.gdgm.blog.bean.Message;
import com.gdgm.blog.common.result.MessageResult;
import com.gdgm.blog.common.util.Page;
import com.gdgm.blog.mapper.MessageMapper;
import com.gdgm.blog.service.MemberService;
import com.gdgm.blog.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageMapper messageMapper;

    @Override
    public Page queryPage(Map<String, Object> map) {
        Integer pageno = (Integer)map.get("pageno");
        Integer pagesize = (Integer)map.get("pagesize");
        String keycode = (String)map.get("keycode");
        Page page = new Page(pageno,pagesize);
        Integer startIndex = page.getStartIndex();
        map.put("startIndex",startIndex);
        Integer setTotalsize = messageMapper.queryCountByMap(map);
        page.setTotalsize(setTotalsize);
        List<Message> messages = messageMapper.queryMessageByMap(map);
        page.setMessageList(messages);
        return page;
    }

    @Override
    public Message queryMessageByMessageid(Integer id) {
        return messageMapper.queryMessageByMessageid(id);
    }

    @Override
    public Integer updateMessageById(Integer id,String reply) {
        return messageMapper.updateMessageById(id,reply);
    }

    @Override
    public Integer deleteMessageById(Integer id) {
        return messageMapper.deleteMessageById(id);
    }

    @Override
    public void deleteMessageByMemberid(Integer id) {
        messageMapper.deleteMessageByMemberid(id);
    }

    @Override
    public Page loadMessage(Map<String, Object> map) {
        Integer pageno = (Integer)map.get("pageno");
        Integer pagesize = (Integer)map.get("pagesize");
        Page page = new Page(pageno,pagesize);
        Integer startIndex = page.getStartIndex();
        map.put("startIndex",startIndex);
        Integer setTotalsize = messageMapper.queryCountByMap(map);
        page.setTotalsize(setTotalsize);
        List<MessageResult> messageResults = messageMapper.loadMessage(map);
        page.setMessageResultList(messageResults);
        return page;
    }

    @Override
    public void saveMessage(Message message) {
        messageMapper.saveMessage(message);
    }

    @Override
    public Integer queryAllCount() {
        return messageMapper.queryAllCount();
    }
}
