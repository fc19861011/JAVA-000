package io.github.kimmking.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;

import java.util.Map;

public class UriHeaderFilter implements HttpRequestFilter {
    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        //获取Netty内置的请求头对象
        HttpHeaders header = fullRequest.headers();
        header.add("nio","fc");
    }
}
