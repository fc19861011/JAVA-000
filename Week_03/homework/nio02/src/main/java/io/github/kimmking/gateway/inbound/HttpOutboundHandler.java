package io.github.kimmking.gateway.inbound;

import io.github.kimmking.gateway.outbound.okhttp.OkhttpOutboundHandler;
import io.github.kimmking.gateway.util.PropertiesUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpOutboundHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger = LoggerFactory.getLogger(HttpOutboundHandler.class);
    private final String proxyServer;
    private io.github.kimmking.gateway.outbound.httpclient4.HttpOutboundHandler handler;

    public HttpOutboundHandler(String proxyServer) {
        this.proxyServer = proxyServer;
        this.handler = new io.github.kimmking.gateway.outbound.httpclient4.HttpOutboundHandler(proxyServer);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        String dual = PropertiesUtil.getValue("respone.dual");
        try {
            FullHttpRequest fullRequest = (FullHttpRequest) msg;
            System.out.println("##################22222");
            switch (dual) {
                case "okhttp":
                    OkhttpOutboundHandler okhttpOutboundHandler = new OkhttpOutboundHandler();
                    okhttpOutboundHandler.handle(fullRequest, ctx);
                    break;
                default:
                    handler.handle(fullRequest, ctx);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
