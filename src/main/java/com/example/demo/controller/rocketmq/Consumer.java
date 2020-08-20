package com.example.demo.controller.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.TopicConfig;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.apache.rocketmq.tools.admin.DefaultMQAdminExt;
import org.apache.rocketmq.tools.command.CommandUtil;

import java.util.List;

/**
 * @author 35716 <a href="xiaopeng.miao@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2020/07/13 17:54
 * desc :
 */
public class Consumer {

    public static void main(String[] args) throws Exception {
        method();
//        DefaultMQPushConsumer cs = new DefaultMQPushConsumer("cs");
//        cs.setNamesrvAddr("localhost:9876");
    }

    public static void method() throws Exception {
        DefaultMQPushConsumer defaultMQAdminExt = new DefaultMQPushConsumer("pullConsumer");
        defaultMQAdminExt.setNamesrvAddr("localhost:9876");

        defaultMQAdminExt.subscribe("Producer_topic_test","*");
        defaultMQAdminExt.registerMessageListener(new MessageListenerConcurrently(){

            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                if (msgs!=null){
                    msgs.forEach(item-> System.out.println(String.valueOf(item.getBody())));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        defaultMQAdminExt.start();
    }
}
