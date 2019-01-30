package com.cn.zm.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cn.zm.server.JmsProducer;

@RestController
public class MsgActiveController {

	@Autowired
	private JmsProducer producer;
	
	@GetMapping("/queue_test")
	public String queue_test(){
		Map<String, String> map = new HashMap<>();
		map.put("value", "你就个二逼");
		producer.sendToQueue(map);
		return "queue";
	}
	
	@GetMapping("/topic_test")
	public String topic_test(){
		Map<String, String> map = new HashMap<>();
		map.put("value", "你就个傻帽");
		producer.sendToTopic(map);
		return "topic";
	}
}
