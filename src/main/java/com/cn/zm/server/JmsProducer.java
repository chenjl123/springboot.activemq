package com.cn.zm.server;

import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * 生成者类
 * @author Administrator
 *
 */
@Component
public class JmsProducer {
	@Autowired
    @Qualifier("firstJmsTemplate")
    private JmsTemplate jmsTemplate;
    
    //@Value("${activemq.topic}")
    private String topic = "topic_test";
    
    //@Value("${activemq.queue}")
    private String queue = "queue_test";
    
    private void sendMsg(Destination destination, Message msg) {
        jmsTemplate.convertAndSend(destination, msg);
    }

    /**
     * ptp消息
     * @param data
     */
    public void sendToQueue(Map<String, String> data) {
        ActiveMQQueue mqQueue = new ActiveMQQueue(queue);
        ActiveMQMessage msg = new ActiveMQMessage();
        try {
            msg.setStringProperty("value", data.get("value"));
        } catch (JMSException e) {
            e.printStackTrace();
        }
        sendMsg(mqQueue, msg);
    }

    /**
     * 订阅式消息
     * @param data
     */
    public void sendToTopic(Map<String, String> data) {
        ActiveMQTopic mqTopic = new ActiveMQTopic(topic);
        ActiveMQMessage msg = new ActiveMQMessage();
        try {
            msg.setStringProperty("value", data.get("value"));
        } catch (JMSException e) {
            e.printStackTrace();
        }
        sendMsg(mqTopic, msg);
    }
}
