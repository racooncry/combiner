package com.ikangtai.shenfeng.netty.configuration.tcp;

import com.alibaba.fastjson.JSON;
import com.godness.kannahashimoto.im.domain.dto.ChatMessageDto;
import com.godness.kannahashimoto.im.domain.dto.UserInfo;
import com.godness.kannahashimoto.im.domain.enums.MessageActionEnum;
import com.godness.kannahashimoto.im.domain.enums.MessageUserTypeEnum;
import com.godness.kannahashimoto.im.domain.pojo.DataContent;
import com.godness.kannahashimoto.im.domain.pojo.Extend;
import com.godness.kannahashimoto.im.handle.UserChannelRelation;
import com.godness.kannahashimoto.im.service.customer.CustomerService;
import com.godness.kannahashimoto.im.service.messageService.MessageService;
import com.godness.kannahashimoto.im.service.servicer.ServicerService;
import com.godness.kannahashimoto.im.utils.SpringUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 处理消息的handler
 * TextWebSocketFrame: 在netty中，适用于为websocket专门处理文本的对象，frame是消息的载体
 */
@DependsOn("springUtil")
@Slf4j
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    // 用于记录和管理所有客户端的channel
    public static ChannelGroup users = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        // 获取客户端传输过来的消息
        String content = textWebSocketFrame.text();
        //  log.info("websocket message:{}", content);
        Channel currentChannel = channelHandlerContext.channel();

        // 1、获取客户端发来的消息
        DataContent dataContent = JSON.parseObject(content, DataContent.class);
        Integer action = dataContent.getAction();
        // 2、检测消息类型，根据不同的类型处理不同的业务
        // 2.1 当websocket第一次open的时候，初始化channel,把用户的channel和userId关联起来
        if (action == MessageActionEnum.CONTENT.type) {
            String senderId = dataContent.getChatMessage().getSenderUserId();
            UserChannelRelation.put(senderId, currentChannel);
            Extend extend = dataContent.getExtend();

            ChatMessageDto chatMessageDto = dataContent.getChatMessage();
            String channelId = this.getChannelLongId(currentChannel);
            if (extend != null && MessageUserTypeEnum.SERVICE.getType() == extend.getService()) {
                log.info("service : {} , online", senderId);
                // 如果是客服第一次建立连接的话,记录channel和serviceId
                ServicerService servicerService = (ServicerService) SpringUtil.getBean("servicerService");
                servicerService.handleOnOpen(channelId, senderId);
                return;
            }


            // 如果是用户第一次建立连接的话
            CustomerService customerService = (CustomerService) SpringUtil.getBean("customerService");
            UserInfo customer = extend.getCustomer();
            log.info("customer : {} , online, message:{}", senderId, customer);
            customerService.handleOnOpen(chatMessageDto, customer, channelId);


        } else if (action == MessageActionEnum.CHAT.type) {
            // 2.2 聊天类类型的消息 ，把聊天记录保存到数据库，同时标记消息的签收状态【未签收】
            Extend extend = dataContent.getExtend();
            ChatMessageDto chatMessageDto = dataContent.getChatMessage();
            // 如果是客服发送消息给用户
            if (extend != null && MessageUserTypeEnum.SERVICE.getType() == extend.getService()) {
                ServicerService servicerService = (ServicerService) SpringUtil.getBean("servicerService");
                servicerService.handleChat(chatMessageDto);
                return;
            }
            // 如果是用户发送消息给客服
            CustomerService customerService = (CustomerService) SpringUtil.getBean("customerService");
            customerService.handleChat(chatMessageDto);
            return;
        } else if (action == MessageActionEnum.SIGNED.type) {
            // 2.3 签收消息类型，针对具体的消息进行签收，修改数据库中 对应消息的签收状态【已签收】
            MessageService messageService = (MessageService) SpringUtil.getBean("messageService");
            // 扩展字段在signed类型的消息中，代表需要去签收的消息id，逗号间隔
            List<String> messageIdList = this.getMessageIdOrUserIds(dataContent);
            if (!CollectionUtils.isEmpty(messageIdList)) {
                // 批量签收消息，注意此处客服和用户都有可能在此处签收消息
                messageService.updateMessageSigned(messageIdList);
            }

        } else if (action == MessageActionEnum.KEEPALIVE.type) {
            // 2.4 心跳类型的消息
            //   System.out.println("收到来自channel为：[" + currentChannel + "]的心跳包...");
        } else if (action == MessageActionEnum.SERVICE_SIGNED.type) {
            // 只有客服在此处签收消息，注意同时会更新用户发给空客服的签收的修改
            MessageService messageService = (MessageService) SpringUtil.getBean("messageService");
            String senderId = dataContent.getChatMessage().getSenderUserId();
            List<String> userIds = this.getMessageIdOrUserIds(dataContent);
            if (!CollectionUtils.isEmpty(userIds)) {
                messageService.updateMessageSignedForService(userIds, senderId);
            }
        }
    }


//        for(Channel channel:clients){
//            channel.writeAndFlush(
//                    new TextWebSocketFrame(
//                            "[服务器在:]"+ LocalDateTime.now()+
//                            "接收到消息,消息为："+content));
//        }
    // 下面调用这个方法和上面for循环一致
//        users.writeAndFlush(new TextWebSocketFrame(
//                "[服务器在:]"+ LocalDateTime.now()+
//                        "接收到消息,消息为："+content));


    public void sendMessage(String receiverId, String message) {
        // 发送消息
        // 从全局用户channel关系中获取接收方的channel
        Channel receiverChannel = UserChannelRelation.get(receiverId);
        if (receiverChannel == null) {
            // TODO: channel为空代表用户离线，推送消息（Jpush,个推，小米推送）
        } else {
            // 当receiver不为空的时候，从ChannelGroup去查找对应channel是否存在
            Channel findChannel = users.find(receiverChannel.id());
            if (findChannel != null) {
                // 用户在线
                receiverChannel.writeAndFlush(
                        new TextWebSocketFrame(
                                message));
            } else {
                // 用户离线 TODO 推送消息
            }
        }
    }

    public List<String> getMessageIdOrUserIds(DataContent dataContent) {
        String messageIdsStr = dataContent.getChatMessage().getId();
        String[] messageIds = messageIdsStr.split(",");
        List<String> messageIdList = new ArrayList<>();
        for (String mid : messageIds) {
            if (!StringUtils.isEmpty(mid)) {
                messageIdList.add(mid);
            }
        }
        return messageIdList;
    }


    /**
     * 当客户端连接服务端之后（打开连接）
     * 获取客户端的channl，并且放到ChannelGroup中进行管理
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        users.add(ctx.channel());
        //     super.handlerAdded(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        users.remove(ctx.channel());
        // super.handlerRemoved(ctx);
        // 当触发handlerRemoved,ChannelGroup会自动移除对应客户端的channel
        Channel channel = ctx.channel();
        this.removeActive(channel);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //  super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        Channel channel = ctx.channel();
        users.remove(channel);
        this.removeActive(channel);

    }

    /**
     * 清除在线状态,注意此处分客服还是用户，对应有不同的逻辑
     */
    private void removeActive(Channel channel) {
        String channelId = this.getChannelLongId(channel);
        ServicerService servicerService = (ServicerService) SpringUtil.getBean("servicerService");
        servicerService.removeChannel(channelId);
    }

    private String getChannelLongId(Channel channel) {
        return channel.id().asLongText();
    }
}
