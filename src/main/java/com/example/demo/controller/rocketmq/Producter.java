package com.example.demo.controller.rocketmq;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.impl.MQAdminImpl;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.apache.rocketmq.tools.admin.DefaultMQAdminExt;

import java.io.IOException;
import java.util.UUID;

/**
 * @author 35716 <a href="xiaopeng.miao@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2020/07/13 16:02
 * desc :
 */
public class Producter {
    public static  void method() throws Exception {

        DefaultMQProducer producer = new DefaultMQProducer("s2");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();
        //producer.createTopic("broker-a","user_test_topic2020",20);
        Message msg = new Message();
        msg.setTopic("user_test_topic2020");
        msg.setTags("mytag");
        msg.setBody("mybody".getBytes());
        producer.send(msg);
    }
    public static void main(String[] args) throws Exception {
        //Instantiate with a producer group name.
        System.setProperty("org.apache.rocketmq.client.sendSmartMsg","false");
    //    method();
    }
}
