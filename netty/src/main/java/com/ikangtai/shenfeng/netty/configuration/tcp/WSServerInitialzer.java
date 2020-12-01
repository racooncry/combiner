package com.ikangtai.shenfeng.netty.configuration.tcp;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

public class WSServerInitialzer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline=socketChannel.pipeline();

        // websocket 基于http协议，所以要有http编解码器
        pipeline.addLast(new HttpServerCodec());

        // 对写大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());

        // 对httpMessage进行聚合，聚合成FullHttpRequest或FullHttpResponse
        // 几乎在netty中的变成，都会使用到此handler
        pipeline.addLast(new HttpObjectAggregator(1024*64));

        // ===================== 以上是用于支持http协议 =================


        // ===================== 增加心跳支持start =================
        // 针对客户端，如果在1分钟没有向服务端发送读写心跳（ALL），则主动断开
        // 如果是读空闲或者是写空闲，则不做处理
        // 2,4,6单位s，生产环境20,40,60
        pipeline.addLast(new IdleStateHandler(20,40,60));
        // 自定义空闲状态检测
        pipeline.addLast(new HeartBeatHandler());
        // ===================== 增加心跳支持end =================




        // websocket服务器处理的协议，用于指定给客户端连接访问的路由 ： /ws
        // 本handler会帮你处理一些繁重的复杂的事
        // 会帮你处理一些握手动作: handshaking(close ,ping,pong) ping+pong=心跳
        // 对于websocket来讲,都是以frames进行传输的，不同的数据类型的对应的frames也不同
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));


        // 自定义handler
        pipeline.addLast(new ChatHandler());


    }
}
