package com.test;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author WXJ
 * @descrption
 * Java8 内置的四大核心函数式接口
 *
 * Comsumer<T>：消费型接口
 *      void accept(T t);
 *
 * Supplier<T>:供给型接口
 *      T get();
 *
 * Function<T,R>:函数型接口
 *      R apply(T t);
 *
 * Predicate<T>:断言型接口
 *      boolean test(T t);
 *
 * @create 2020/4/15 16:52
 **/
public class TestLambda3 {

    //Comsumer<T> 消费型接口
    @Test
    public void test1(){
        happy(10000, m -> System.out.println("消费："+m));
    }

    public void happy(double money, Consumer<Double> con){
        con.accept(money);
    }

//    Supplier<T>:供给型接口
    @Test
    public void test2(){
        List<Integer> list = getNumList(10,() -> (int)(Math.random() * 100));
        for (Integer integer:list){
            System.out.println(integer);
        }
    }

    //需求：产生指定个数的整数，并放入集合中
    public List<Integer> getNumList(int num, Supplier<Integer> supplier){
        List<Integer> list = new ArrayList<>();
        for (int i = 0 ; i<num;i++){
            Integer n= supplier.get();
            list.add(n);
        }
        return list;
    }


//    Function<T,R>:函数型接口
    @Test
    public void test3(){
        String string = strHandler("\t\t\t 6666",str -> str.trim());
        System.out.println(string);
    }

    //需求：用于处理字符串
    public String strHandler(String str, Function<String,String> fun){
        return fun.apply(str);
    }


//    Predicate<T>:断言型接口
    @Test
    public void test4(){
        List<String> list = Arrays.asList("Hello","Lambda","www","ok");
       List<String> stringList =  filterStr(list, s -> s.length() > 3);

        for (String ss:stringList){
            System.out.println(ss);
        }
    }

    //需求：将满足条件的字符串放入集合中
    public List<String> filterStr(List<String> list, Predicate<String> pre){
        List<String> stringList = new ArrayList<>();

        for (String str : list){
            if (pre.test(str)){
                stringList.add(str);
            }
        }

        return stringList;
    }



}
