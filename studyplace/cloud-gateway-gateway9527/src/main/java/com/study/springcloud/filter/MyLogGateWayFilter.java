package com.study.springcloud.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * @ClassName MyLogGateWayFilter
 * @Description
 * @Author Administrator
 * @Date 2020/4/4 0:49
 * @Version 1.0
 **/
@Component
@Slf4j
public class MyLogGateWayFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("come in MyLogGateWayFilter: "+new Date());
        String name = exchange.getRequest().getQueryParams().getFirst("uname");
        if (name == null)
        {
            log.info("用户名为null,非法用户,返回结果");
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return exchange.getResponse().setComplete();
        }
        log.info("合法用户，进入下一步操作");
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
