/**
 * XXX.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.xiajun.test.nettytest.EchoServer;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * 客户端
 * 
 * @author xiajun.xj
 * @version $Id: EchoClient.java, v 0.1 2014年11月14日 下午4:39:15 xiajun.xj Exp $
 */
public class EchoClient {
    private final String host;
    private final int    port;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws Exception {
        /*
         * A Bootstrap instance is created to bootstrap the client.
         * The NioEventLoopGroup instance is created and assigned to handle the event
         * processing, such as creating new connections, receiving data, writing data, and so on.
         * The remote InetSocketAddress to which the client will connect is specified.
         * A handler is set that will be executed once the connection is established.
         * After everything is set up, the ServerBootstrap.connect() method is called to
         * connect to the remote peer (the echo-server in our case).         * 
         */
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                .remoteAddress(new InetSocketAddress(host, port))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new EchoClientHandler());
                    }
                });
            ChannelFuture f = b.connect().sync();
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws Exception {
        //        if (args.length != 2) {
        //            System.err.println("Usage: " + EchoClient.class.getSimpleName() + " <host> <port>");
        //            return;
        //        }
        // Parse options.
        //        final String host = args[0];
        //        final int port = Integer.parseInt(args[1]);
        final String host = "127.0.0.1";
        final int port = 8001;
        new EchoClient(host, port).start();
    }
}