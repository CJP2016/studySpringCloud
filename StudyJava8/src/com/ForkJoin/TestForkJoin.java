package com.ForkJoin;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

/**
 * @author WXJ
 * @descrption
 * @create 2020/4/16 16:56
 **/
public class TestForkJoin {

    /*
    * ForkJoin框架
    * 借用其他线程来完成子任务
    *
    * 数值越大时间越小
    * */
    @Test
    public void test1(){
        Instant start = Instant.now();

        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Long> task = new ForkJoinCalculate(0,500000000L);
        Long sum= pool.invoke(task);
        System.out.println(sum);
        Instant end = Instant.now();

        System.out.println("耗费时间为： "+Duration.between(start,end).toMillis());//63  421
    }

    /*
    * 普通 for循环
    * */
    @Test
    public void test2(){
        Instant start = Instant.now();
        long sum = 0L;

        for (long i = 0;i<500000000L;i++){
            sum += i;
        }
        System.out.println(sum);
        Instant end = Instant.now();
        System.out.println("耗费时间为： "+Duration.between(start,end).toMillis());//16  405
    }

    /**
     * JAVA8 并行流
     * 速度更快
     * */
    @Test
    public void test3(){
        Instant start = Instant.now();
        LongStream.range(0,500000000L)
                  .parallel()
                  .reduce(0,Long::sum);
        Instant end = Instant.now();
        System.out.println("耗费时间为： "+Duration.between(start,end).toMillis());//16  390
    }
}
