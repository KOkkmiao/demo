package com.example.demo.controller.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;

import java.util.logging.Logger;

/**
 * @author 35716 苗小鹏 <a href="xiaopeng.miao@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2020/05/19 15:00
 * desc :
 */
public class TimeClientHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = Logger
            .getLogger(TimeClientHandler.class.getName());

    private final ByteBuf firstMessage;

    /**
     * Creates a client-side handler.
     */
    public TimeClientHandler() {
        byte[] req = "QUERY TIME ORDER".getBytes();
        firstMessage = Unpooled.buffer(req.length);
        firstMessage.writeBytes(req);
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        //通过此方法将消息发送给服务端
        ctx.writeAndFlush(firstMessage);
    }
    //服务端返回应答消息调用此方法
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req, "UTF-8");
        System.out.println("Now is : " + body);
    }

    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println(msg.getClass().getName());
        ctx.writeAndFlush(firstMessage);
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // 释放资源
        logger.warning("Unexpected exception from downstream : "
                + cause.getMessage());
        ctx.close();
    }
}
