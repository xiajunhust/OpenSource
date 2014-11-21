/**
 * XXX.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.xiajun.test.nettytest.EchoServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * 服务端
 * 
 * @author xiajun.xj
 * @version $Id: EchoServer.java, v 0.1 2014年11月14日 下午3:55:50 xiajun.xj Exp $
 */
public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        /*the import things:        
         * You create a ServerBootstrap instance to bootstrap the server and bind it later.
         * You create and assign the NioEventLoopGroup instances to handle event processing,
         * such as accepting new connections, receiving data, writing data, and so on.
         * You specify the local InetSocketAddress to which the server binds.
         * You set up a childHandler that executes for every accepted connection.
         * After everything is set up, you call the ServerBootstrap.bind() method to bind the
         * server.
        */

        //to accept new connections and handle acceted connections
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            //bootstraps the server
            ServerBootstrap b = new ServerBootstrap();
            b.group(group).channel(NioServerSocketChannel.class)//specify the channel type
                .localAddress(new InetSocketAddress(port))//bind the address
                .childHandler(new ChannelInitializer<SocketChannel>() {//specify the handler to call when a connection is accpeted
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            //channelPipeline holds all of the different ChannelHandlers of a channel
                            ch.pipeline().addLast(new EchoServerHandler());
                        }
                    });

            //bind the server and wait until the bind completes
            //the call to the sync will cause this to block until the server is bound
            ChannelFuture f = b.bind().sync();
            System.out.println(EchoServer.class.getName() + "started and listen on "
                               + f.channel().localAddress());
            //wait until the server's channel closes
            f.channel().closeFuture().sync();
        } finally {
            //shutdown the eventLoopGroup and release all resources
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage: " + EchoServer.class.getSimpleName() + "<port>");
        }
        //        int port = Integer.parseInt(args[0]);
        int port = 8001;
        new EchoServer(port).start();
    }
}