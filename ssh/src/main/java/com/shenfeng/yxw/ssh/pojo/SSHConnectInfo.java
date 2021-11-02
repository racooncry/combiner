package com.shenfeng.yxw.ssh.pojo;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import org.springframework.web.socket.WebSocketSession;

/**
 * @Author: yangxiaowei37
 * @Date: 2/11/2021 下午3:28
 * @Version: 1.0
 * @Description:
 */
public class SSHConnectInfo {
    private WebSocketSession webSocketSession;
    private JSch jSch;
    private Channel channel;


    public WebSocketSession getWebSocketSession() {
        return webSocketSession;
    }

    public void setWebSocketSession(WebSocketSession webSocketSession) {
        this.webSocketSession = webSocketSession;
    }

    public JSch getjSch() {
        return jSch;
    }

    public void setjSch(JSch jSch) {
        this.jSch = jSch;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
