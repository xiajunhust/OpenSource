/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.xiajun.test.nettytest.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.TooLongFrameException;

import org.junit.Assert;
import org.junit.Test;

import com.xiajun.test.nettytest.decoder.FrameTrunkDecoder;

/**
 * 
 * @author xiajun.xj
 * @version $Id: FrameTrunkDecoderTest.java, v 0.1 2014年11月21日 下午5:39:15 xiajun.xj Exp $
 */
public class FrameTrunkDecoderTest {
    @Test
    public void testFrameDevoded() {
        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
            buf.writeByte(i);
        }

        ByteBuf input = buf.duplicate();

        EmbeddedChannel channel = new EmbeddedChannel(new FrameTrunkDecoder(3));
        Assert.assertTrue(channel.writeInbound(input.readBytes(2)));

        try {
            channel.writeInbound(input.readBytes(4));
            Assert.fail();
        } catch (TooLongFrameException e) {
            // TODO: handle exception
        }

        Assert.assertTrue(channel.writeInbound(input.readBytes(3)));

        Assert.assertTrue(channel.finish());
        Assert.assertEquals(buf.readBytes(2), channel.readInbound());
        Assert.assertEquals(buf.skipBytes(4).readBytes(3), channel.readInbound());
    }
}
