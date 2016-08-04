package com.zsl.rpc;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class MqClient {
	public static void main(String[] args) throws Exception, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		factory.setUsername("guest");
		factory.setPassword("guest");
		factory.setPort(AMQP.PROTOCOL.PORT);
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		String replyQueueName = channel.queueDeclare().getQueue();
		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(replyQueueName, true, consumer);
		
		
		
		//发送rpc请求
		String response=null;
		String corrId = java.util.UUID.randomUUID().toString();
		//发送请求消息,消息使用了两个属性：replyto和correlationId
		BasicProperties props=new BasicProperties.Builder()
		.correlationId(corrId).replyTo(replyQueueName).build();  
	}
}
