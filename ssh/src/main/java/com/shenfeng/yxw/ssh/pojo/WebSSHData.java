package com.shenfeng.yxw.ssh.pojo;

/**
 * @Author: yangxiaowei37
 * @Date: 2/11/2021 下午3:28
 * @Version: 1.0
 * @Description:
 */
public class WebSSHData {
    // 操作
    private String operate;
    private String host;
    // 端口号默认为22
    private Integer port = 22;
    private String username;
    private String password;
    private String command = "";

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}