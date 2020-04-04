package com.study.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * @ClassName ReveiveMessage
 * @Description
 * @Author Administrator
 * @Date 2020/4/4 14:35
 * @Version 1.0
 **/
@Slf4j
@Component
@EnableBinding(Sink.class)
public class ReveiveMessageListenerController {

    @Value("${server.port}")
    private  String serverPort;

    @StreamListener(Sink.INPUT)
    public void input(Message<String> message){
        System.out.println("消费者2号，----->收到的消息"+message.getPayload()+"\t port: "+serverPort);
    }
}
