package com.cn.zm.config;

import javax.jms.ConnectionFactory;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableJms
@EnableAsync
/**
 * 配置activemq
 * @author Administrator
 *
 */
public class JmsConfiguration {
	//@Value("${broker-url}")
	//private String brokerUrl;  //tcp://127.0.0.1:61616
	
	@Bean(name = "firstConnectionFactory")
	public ActiveMQConnectionFactory getFirstConnectionFactory(RedeliveryPolicy redeliveryPolicy) {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL("tcp://127.0.0.1:61616");
		connectionFactory.setUserName("admin");
		connectionFactory.setPassword("admin");
		connectionFactory.setRedeliveryPolicy(redeliveryPolicy);
		return connectionFactory;
	}

	@Bean
	public RedeliveryPolicy redeliveryPolicy(){
	        RedeliveryPolicy  redeliveryPolicy=   new RedeliveryPolicy();
	        //是否在每次尝试重新发送失败后,增长这个等待时间
	        redeliveryPolicy.setUseExponentialBackOff(true);
	        //重发次数,默认为6次   这里设置为10次
	        redeliveryPolicy.setMaximumRedeliveries(10);
	        //重发时间间隔,默认为1秒
	        redeliveryPolicy.setInitialRedeliveryDelay(1);
	        //第一次失败后重新发送之前等待500毫秒,第二次失败再等待500 * 2毫秒,这里的2就是value
	        redeliveryPolicy.setBackOffMultiplier(2);
	        //是否避免消息碰撞
	        redeliveryPolicy.setUseCollisionAvoidance(false);
	        //设置重发最大拖延时间-1 表示没有拖延只有UseExponentialBackOff(true)为true时生效
	        redeliveryPolicy.setMaximumRedeliveryDelay(-1);
	        return redeliveryPolicy;
	}

	@Bean(name = "firstJmsTemplate")
	public JmsTemplate getFirstJmsTemplate(
			@Qualifier("firstConnectionFactory") ConnectionFactory connectionFactory) {
		//JmsMessagingTemplate template = new JmsMessagingTemplate(connectionFactory);
		
		JmsTemplate template = new JmsTemplate(connectionFactory);
		template.setDeliveryMode(2);//进行持久化配置 1表示非持久化，2表示持久化
		template.setSessionTransacted(true); //开启事物
		template.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
		return template;
	}

	@Bean(name = "firstTopicListener")
	public DefaultJmsListenerContainerFactory getFirstTopicListener(
			@Qualifier("firstConnectionFactory") ConnectionFactory connectionFactory) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setPubSubDomain(true); // if topic, set true
		// factory.setSessionAcknowledgeMode(4); // change acknowledge mode
		return factory;
	}

	@Bean(name = "firstQueueListener")
	public DefaultJmsListenerContainerFactory getFirstQueueListener(
			@Qualifier("firstConnectionFactory") ConnectionFactory connectionFactory) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		// factory.setSessionAcknowledgeMode(4); // change acknowledge mode
		return factory;
	}
}
