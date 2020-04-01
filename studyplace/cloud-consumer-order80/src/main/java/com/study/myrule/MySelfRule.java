package com.study.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName MyselfRule
 * @Description
 * @Author Administrator
 * @Date 2020/4/1 20:47
 * @Version 1.0
 **/
@Configuration
public class MySelfRule {

    /**
     * @Author kipp
     * @Description
     * RoundRobinRule  轮询
     * RandomRule  随机
     * RetryRule  先按照轮询的策略获取服务，如果失败则指定
     * WeightedResponseTimeRule 对轮询的扩展，响应速度越快的实例选择权重越大，越易选中
     * BestAvailableRule  先过滤由于多次访问故障而处于断路器跳闸状态的服务，再选一个并发量最小的
     * AvailabilityFilteringRule 先过滤故障实例，再选择并发较小的实例
     * ZoneAvoidanceRule 默认规则，复合判断server所在区域的性能和server的可用性选择服务器
     * @Date 20:48 2020/4/1
     * @Param
     * @return
     **/

    @Bean
    public IRule myRule(){
        return new RandomRule();
    }
}
