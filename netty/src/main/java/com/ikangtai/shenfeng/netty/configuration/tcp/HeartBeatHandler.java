package com.ikangtai.shenfeng.netty.configuration.tcp;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;


/**
 * 用于检测channel的心跳handler
 * 继承ChannelInboundHandlerAdapter,从而不需要实现channelRead0的方法
 */
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        //  super.userEventTriggered(ctx, evt);
        // 判断evt是否是IdleStateEvent(用于触发用户时间，包含读空闲/写空闲/读写空闲)
        if (evt instanceof IdleStateEvent) {
            //强制类型转换
            IdleStateEvent event = (IdleStateEvent) evt;

            if (event.state() == IdleState.READER_IDLE) {
//                System.out.println("进入读空闲");
            } else if (event.state() == IdleState.WRITER_IDLE) {
//                System.out.println("进入写空闲");
            } else if (event.state() == IdleState.ALL_IDLE) {
                Channel channel = ctx.channel();
                System.out.println("channel关闭前的数量："+ ChatHandler.users.size());
                //关闭无用的channel以防资源浪费
                channel.close();
                System.out.println("channel关闭后的数量："+ ChatHandler.users.size());
            }

        }
    }
}
