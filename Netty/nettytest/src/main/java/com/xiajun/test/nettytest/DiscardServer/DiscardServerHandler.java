/**
 * XXX.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.xiajun.test.nettytest.DiscardServer;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Handles a server-side channel.
 * 
 * @author xiajun.xj
 * @version $Id: DiscardServerHandler.java, v 0.1 2014年11月3日 下午8:16:25 xiajun.xj Exp $
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter { // (1)

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
        //[1] A discard sercer
        /*        ByteBuf in = (ByteBuf) msg;
                try {
                    while (in.isReadable()) { // (1)
                        System.out.print((char) in.readByte());
                        System.out.flush();
                    }
                } finally {
                    ReferenceCountUtil.release(msg); // (2)
                }*/

        //[2] A Echo server
        ctx.write(msg); // (1)
        ctx.flush(); // (2)
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}