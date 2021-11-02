package com.shenfeng.yxw.ssh.conf;


import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;
import java.util.UUID;

/**
 * @Author: yangxiaowei37
 * @Date: 2/11/2021 下午3:16
 * @Version: 1.0
 * @Description:
 */
public class WebSocketInterceptor implements HandshakeInterceptor {
    /**
     * @Description: Handler处理前调用
     * @Param: [serverHttpRequest, serverHttpResponse, webSocketHandler, map]
     * @return: boolean
     * @Author: NoCortY
     * @Date: 2020/3/1
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        if (serverHttpRequest instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest request = (ServletServerHttpRequest) serverHttpRequest;
            // 生成一个UUID，这里由于是独立的项目，没有用户模块，所以可以用随机的UUID
            // 但是如果要集成到自己的项目中，需要将其改为自己识别用户的标识
            String uuid = UUID.randomUUID().toString().replace("-", "");
            // 将uuid放到websocketsession中
            map.put(ConstantPool.USER_UUID_KEY, uuid);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

    }
}
