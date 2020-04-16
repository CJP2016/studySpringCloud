package com.Stream;

import com.test.Employee;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author WXJ
 * @descrption
 *什么是Stream
 * Stream是数据渠道，用于操作数据源所生成的元素序列
 * 注意：
 * Stream 自己不会存储元素
 * Stream 不会改变源对象。相反，他们会返回一个持有结果的新Stream
 * Stream 操作是延迟执行的，这意味着他们会等到需要结果的时候才执行。
 *
 * 三个步骤
 *  1.创建Stream（数据源（集合、数组等）转换成流）
 *      ↓
 *  2.中间操作（一系列流水线式的中间操作，对数据源的数据进行处理） Filter、map
 *      ↓
 *  3.终止操作（产生一个新流）
 *
 * @create 2020/4/16 14:46
 **/
public class TestStream {

    //创建Stream流
    @Test
    public void test1(){
        //1.可以通过Collection 系列集合提供的 串行  stream() 或  并行 parallelStream()  获取
        List<String> list = new ArrayList<>();
        Stream<String> stream1 = list.stream();

        //2.通过 Arrays 中的静态方法获取数组流
        Employee[] employees = new Employee[10];
        Stream<Employee> stream2 = Arrays.stream(employees);

        //3.通过Stream 类的静态方法获取
        Stream<String> stream3 = Stream.of("aa","bb","cc");

        //4.创建无限流
        //迭代
        Stream<Integer> stream4 = Stream.iterate(0,(x) -> x + 2);
        stream4.limit(10).forEach(System.out::println);

        //生成
        Stream.generate(() -> (int)(Math.random())).limit(5).forEach(System.out::println);
    }
}
