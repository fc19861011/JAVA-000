package io.github.kimmking.gateway.inbound;

import io.github.kimmking.gateway.filter.UriHeaderFilter;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;

public class FilterInboundHandler extends ChannelInboundHandlerAdapter {
    private UriHeaderFilter uriHeaderFilter;

    public FilterInboundHandler() {
        this.uriHeaderFilter = new UriHeaderFilter();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpRequest fullRequest = (FullHttpRequest) msg;
        if(!fullRequest.uri().contains("favicon.ico")) {
            this.uriHeaderFilter.filter(fullRequest, ctx);
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        Channel channel = ctx.channel();
        if (channel.isActive()) {
            ctx.close();
        }
    }
}
