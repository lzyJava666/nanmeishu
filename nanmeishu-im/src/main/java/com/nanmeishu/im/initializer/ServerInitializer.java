package com.nanmeishu.im.initializer;

import com.nanmeishu.im.handler.ImServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class ServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //编码器
        pipeline.addLast(new HttpServerCodec());
        //已块的方式写入
        pipeline.addLast(new ChunkedWriteHandler());
        //HTTP数据传输的多段的，此方法可以使之聚合
        pipeline.addLast(new HttpObjectAggregator(8192));
        /**
         * 将http协议升级为ws协议，保持长连接
         */
        pipeline.addLast(new WebSocketServerProtocolHandler("/im"));
        //handler
        pipeline.addLast(new ImServerHandler());
    }
}
