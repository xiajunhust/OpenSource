/**
 * XXX.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.xiajun.test.nettytest.EchoServer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * handler����Ҫ�߼�����
 * 
 * @author xiajun.xj
 * @version $Id: EchoServerHandler.java, v 0.1 2014��11��14�� ����4:13:35 xiajun.xj Exp $
 */
//Sharedע�⣺�ڲ�ͬ��channel֮�乲��
@Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * ����Ψһһ�����뱻���صķ�����
     * 
     * ���յ����ݺ�Ĵ����߼� ��
     * �����յ�������д�ء���Ҫע����ǣ����ﲻ������flush���ݵ�Զ�̿ͻ��ˡ�  
     * 
     * @see io.netty.channel.ChannelInboundHandlerAdapter#channelRead(io.netty.channel.ChannelHandlerContext, java.lang.Object)
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf) msg;
        System.out.println("Server received: "
                           + /*ByteBufUtil.hexDump*/(in.toString(CharsetUtil.UTF_8)));
        ctx.write(in);

        //�ͷ���Դ���˴������ͷš�
        //ReferenceCountUtil.release(msg);
    }

    /**
     *  flush��ǰ����д�����ݵ�Զ�̿ͻ��ˡ������ڲ������֮��ر�channel��
     * 
     * @see io.netty.channel.ChannelInboundHandlerAdapter#channelReadComplete(io.netty.channel.ChannelHandlerContext)
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * �쳣������ӡ��־���ر�channel�� 
     * ����Ҫ��һ��ChannelHandlerʵ�ִ˷���
     * 
     * @see io.netty.channel.ChannelHandlerAdapter#exceptionCaught(io.netty.channel.ChannelHandlerContext, java.lang.Throwable)
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
