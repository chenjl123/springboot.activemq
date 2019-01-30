package com.cn.zm.server;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 消费者
 * @author Administrator
 *
 */
@Component
public class JmsConsumer {
	    @JmsListener(destination = "topic_test", containerFactory = "firstTopicListener")
	    @Async // receive msg asynchronously
	    //@Async("taskExecutePool") 
	    public void receiveTopic(Message msg, Session session) throws JMSException {
	        try {
	            Thread.sleep(1000L);
	            int a = 1 / 0;
	            msg.acknowledge(); //消息确认
	            //session.commit();  事物会报错，不知道为啥
	        } catch (Exception e) {
	        	System.out.println("事物回滚");
	        	//session.rollback();
	            e.printStackTrace();
	        }
	        System.out.println(Thread.currentThread().getName() + ": topic=====one======" + msg.getStringProperty("value"));
	    }
	    
//	    @JmsListener(destination = "topic_test", containerFactory = "firstTopicListener")
//	    @Async // receive msg asynchronously
//	    //@Async("taskExecutePool") 
//	    public void receiveTopicTwo(Message msg) throws JMSException {
//	        try {
//	            Thread.sleep(1000L);
//	            // msg.acknowledge(); //消息确认
//	        } catch (InterruptedException e) {
//	            e.printStackTrace();
//	        }
//	        System.out.println(Thread.currentThread().getName() + ": topic======two=====" + msg.getStringProperty("value"));
//	    }
	    
	    @JmsListener(destination = "queue_test", containerFactory = "firstQueueListener")
	    @Async
	    public void receiveQueue(Message msg) throws JMSException {
	        try {
	            Thread.sleep(1000L);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	        System.out.println(Thread.currentThread().getName() + ": Queue=====one======" + msg.getStringProperty("value"));
	    }
	    
	    @JmsListener(destination = "queue_test", containerFactory = "firstQueueListener")
	    @Async
	    public void receiveQueueTwo(Message msg) throws JMSException {
	        try {
	            Thread.sleep(1000L);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	        System.out.println(Thread.currentThread().getName() + ": Queue=====two======" + msg.getStringProperty("value"));
	    }
}
