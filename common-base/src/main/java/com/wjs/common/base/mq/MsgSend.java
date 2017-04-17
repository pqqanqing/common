package com.wjs.common.base.mq;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by panqingqing on 16/7/14.
 */
public class MsgSend {

    public static final String KEY_GROUP = "groupId";
    public static final String KEY_PARAMS = "params";
    public static final String KEY_CONTACTS = "contacts";

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendMsg(Long groupId, Map<String, Object> params, List<String> contacts) {
        sendMsg("msg", groupId, params, contacts);
    }

    public void sendMsg(String queueName, Long groupId, Map<String, Object> params, List<String> contacts) {
        final String message = getJson(groupId, params, contacts);
        jmsTemplate.send(queueName, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(message);
            }
        });
    }

    public String getJson(Long groupId, Map<String, Object> params, List<String> contacts) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(KEY_GROUP, groupId);
        map.put(KEY_PARAMS, params);
        map.put(KEY_CONTACTS, contacts);
        return JSONObject.toJSONString(map);
    }

}
