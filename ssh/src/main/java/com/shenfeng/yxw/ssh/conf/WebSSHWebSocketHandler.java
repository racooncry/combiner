package com.shenfeng.yxw.ssh.conf;

import com.shenfeng.yxw.ssh.service.WebSSHService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

/**
 * @Author: yangxiaowei37
 * @Date: 2/11/2021 下午3:19
 * @Version: 1.0
 * @Description:
 */
@Slf4j
@Component
public class WebSSHWebSocketHandler implements WebSocketHandler {
    @Autowired
    private WebSSHService webSSHService;


    /**
     * @Description: 用户连接上WebSocket的回调
     * @Param: [webSocketSession]
     * @return: void
     * @Author: Object
     * @Date: 2020/3/8
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        log.info("用户:{},连接WebSSH", webSocketSession.getAttributes().get(ConstantPool.USER_UUID_KEY));
        //调用初始化连接
        webSSHService.initConnection(webSocketSession);
    }

    /**
     * @Description: 收到消息的回调
     * @Param: [webSocketSession, webSocketMessage]
     * @return: void
     * @Author: NoCortY
     * @Date: 2020/3/8
     */
    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        if (webSocketMessage instanceof TextMessage) {
            log.info("用户:{},发送命令:{}", webSocketSession.getAttributes().get(ConstantPool.USER_UUID_KEY), webSocketMessage.toString());
            //调用service接收消息
            webSSHService.recvHandle(((TextMessage) webSocketMessage).getPayload(), webSocketSession);
        } else if (webSocketMessage instanceof BinaryMessage) {

        } else if (webSocketMessage instanceof PongMessage) {

        } else {
            System.out.println("Unexpected WebSocket message type: " + webSocketMessage);
        }
    }

    /**
     * @Description: 出现错误的回调
     * @Param: [webSocketSession, throwable]
     * @return: void
     * @Author: Object
     * @Date: 2020/3/8
     */
    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        log.error("数据传输错误");
    }

    /**
     * @Description: 连接关闭的回调
     * @Param: [webSocketSession, closeStatus]
     * @return: void
     * @Author: NoCortY
     * @Date: 2020/3/8
     */
    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        log.info("用户:{}断开webssh连接", String.valueOf(webSocketSession.getAttributes().get(ConstantPool.USER_UUID_KEY)));
        //调用service关闭连接
        webSSHService.close(webSocketSession);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}