package com.gdgm.blog.mapper;

import com.gdgm.blog.bean.Member;
import com.gdgm.blog.bean.Message;
import com.gdgm.blog.common.result.MessageResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MessageMapper {
    Integer queryCountByMap(Map<String, Object> map);

    List<Message> queryMessageByMap(Map<String, Object> map);

    Message queryMessageByMessageid(Integer id);

    Integer updateMessageById(@Param("id") Integer id, @Param("reply")String reply);

    Integer deleteMessageById(Integer id);

    void deleteMessageByMemberid(Integer id);

    List<MessageResult> loadMessage(Map<String, Object> map);

    void saveMessage(Message message);

    Integer queryAllCount();
}
