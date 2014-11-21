/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.xiajun.test.nettytest.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

import java.util.List;

/**
 * ������������һ����ʱ���쳣
 * 
 * @author xiajun.xj
 * @version $Id: FrameTrunkDecoder.java, v 0.1 2014��11��21�� ����5:34:32 xiajun.xj Exp $
 */
public class FrameTrunkDecoder extends ByteToMessageDecoder {
    private final int maxFrameSize;

    public FrameTrunkDecoder(int maxFrameSize) {
        this.maxFrameSize = maxFrameSize;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        int readBytes = in.readableBytes();
        if (readBytes > maxFrameSize) {
            in.clear();
            throw new TooLongFrameException();
        }
        ByteBuf buf = in.readBytes(readBytes);
        out.add(buf);
    }
}
