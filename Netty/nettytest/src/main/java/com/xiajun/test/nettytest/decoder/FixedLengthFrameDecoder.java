/**
 * XXX.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.xiajun.test.nettytest.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * A decoder
 * �����ǣ���������̶���С������
 * 
 * @author xiajun.xj
 * @version $Id: FixedLengthFrameDecoder.java, v 0.1 2014��11��20�� ����9:09:40 xiajun.xj Exp $
 */
public class FixedLengthFrameDecoder extends ByteToMessageDecoder {
    private final int frameLength;

    public FixedLengthFrameDecoder(int frameLength) {
        if (frameLength <= 0) {
            throw new IllegalArgumentException("frameLength must be a positive integer: "
                                               + frameLength);
        }
        this.frameLength = frameLength;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        while (in.readableBytes() >= frameLength) {
            ByteBuf buf = in.readBytes(frameLength);
            out.add(buf);
        }
    }
}
