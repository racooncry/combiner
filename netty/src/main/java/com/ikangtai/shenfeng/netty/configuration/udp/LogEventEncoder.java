package com.ikangtai.shenfeng.netty.configuration.udp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @Author yangxw
 * @Date 2020-12-01 下午2:17
 * @Description
 * @Version 1.0
 */
public class LogEventEncoder extends MessageToMessageEncoder<LogEvent> {
    private final InetSocketAddress remoteAddress;

    public LogEventEncoder(InetSocketAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
    }


    @Override
    protected void encode(ChannelHandlerContext ctx, LogEvent msg, List<Object> out) throws Exception {
        byte[] file = msg.getLogfile().getBytes(CharsetUtil.UTF_8);
        byte[] content = msg.getMsg().getBytes(CharsetUtil.UTF_8);
        ByteBuf byteBuf = ctx.alloc().buffer(file.length + content.length + 1);
        byteBuf.writeBytes(file);
        byteBuf.writeByte(LogEvent.SEPARATOR);
        byteBuf.writeBytes(content);
        out.add(new DatagramPacket(byteBuf, remoteAddress));
    }
}
