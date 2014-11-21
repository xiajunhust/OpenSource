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
 * handler，主要逻辑处理。
 * 
 * @author xiajun.xj
 * @version $Id: EchoServerHandler.java, v 0.1 2014年11月14日 下午4:13:35 xiajun.xj Exp $
 */
//Shared注解：在不同的channel之间共享
@Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 这是唯一一个必须被重载的方法。
     * 
     * 接收到数据后的处理逻辑 。
     * 将接收到的数据写回。需要注意的是，这里不会立即flush数据到远程客户端。  
     * 
     * @see io.netty.channel.ChannelInboundHandlerAdapter#channelRead(io.netty.channel.ChannelHandlerContext, java.lang.Object)
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf) msg;
        System.out.println("Server received: "
                           + /*ByteBufUtil.hexDump*/(in.toString(CharsetUtil.UTF_8)));
        ctx.write(in);

        //释放资源【此处不能释放】
        //ReferenceCountUtil.release(msg);
    }

    /**
     *  flush以前所有写的数据到远程客户端。并且在操作完成之后关闭channel。
     * 
     * @see io.netty.channel.ChannelInboundHandlerAdapter#channelReadComplete(io.netty.channel.ChannelHandlerContext)
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * 异常处理：打印日志，关闭channel。 
     * 至少要有一个ChannelHandler实现此方法
     * 
     * @see io.netty.channel.ChannelHandlerAdapter#exceptionCaught(io.netty.channel.ChannelHandlerContext, java.lang.Throwable)
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
