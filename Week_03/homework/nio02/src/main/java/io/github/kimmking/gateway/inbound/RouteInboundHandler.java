package io.github.kimmking.gateway.inbound;

import io.github.kimmking.gateway.filter.UriHeaderFilter;
import io.github.kimmking.gateway.router.HttpEndpointRouter;
import io.github.kimmking.gateway.router.RoundRibbonRouterImpl;
import io.github.kimmking.gateway.router.WeightRouterImpl;
import io.github.kimmking.gateway.util.PropertiesUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RouteInboundHandler extends ChannelInboundHandlerAdapter {
    private HttpEndpointRouter httpEndpointRouter;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String balance = PropertiesUtil.getValue("gateway.balance");
        log.info("负载均衡模式为:{}", balance);
        if ("weight".equals(balance)) {
            httpEndpointRouter = new WeightRouterImpl();
        } else {
            httpEndpointRouter = new RoundRibbonRouterImpl();
        }
        FullHttpRequest fullRequest = (FullHttpRequest) msg;
        String uri = fullRequest.uri().substring(1);
        log.info(uri);
        String path = uri.substring(0, uri.indexOf("/"));
        String param = uri.substring(uri.indexOf("/"));
        String realPath = httpEndpointRouter.route(path);
        realPath = "http://" + realPath + param;
        log.info("转发地址：{} -> {}", uri, realPath);
        fullRequest.setUri(realPath);
        ctx.fireChannelRead(msg);
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
