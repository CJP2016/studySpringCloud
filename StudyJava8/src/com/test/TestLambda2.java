package com.test;

import org.junit.Test;

import java.util.Comparator;
import java.util.function.Consumer;

/**
 * @author WXJ
 * @descrption
 *  一、Lambda 表达式的基础语法： Java8中引入了一个新的操作符“ -> ” 该操作符称为箭头操作符或Lambda操作符
 *      箭头操作符Lambda 表达式拆分成两部分：
 *      左侧：Lambda 表达式的参数列表
 *      右侧：Lambda 表达式中所需执行的功能，即 Lambda体
 *
 *  语法格式一： 无参数，无返回值
 *      () -> System.out.prinln("Hello Lambda!");
 *
 *  语法格式二：有一个参数，并且无返回值
 *      (x) -> System.out.println(x)
 *
 *  语法格式三：若只有一个参数，小括号可以省略不写
 *      x -> System.out.println(x)
 *
 *  语法格式四：有两个以上的参数，有返回值，并且Lambda体重有多条语句
 *      Comparator<Integer> com = (x,y) -> {
 *             System.out.println("函数接口");
 *             return Integer.compare(x,y);
 *         };
 *         System.out.println(com);
 *
 *   语法格式五：若Lambda体中有只有一条语句，return和大括号都可以省略不写
 *      Comparator<Integer> com = (x,y) -> Integer.compare(x,y);
 *
 *   语法格式六：Lambda表达式的参数列表的数据类型可以省略不写， 因为JVM编译器通过上下文推断出，数据类型，
 * @create 2020/4/14 16:56
 **/
public class TestLambda2 {

    @Test
    public void test1(){
        int num = 0;// java 1.7以前必须是final
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello World!" +  num);
            }
        };
        r.run();

        Runnable r1 = () -> System.out.println("Hello Lambda!" + num);
        r1.run();
    }

    @Test
    public void test2(){
        Consumer<String> con = (x) -> System.out.println(x);
        con.accept("厉害");
    }

    @Test
    public void test3(){
        Consumer<String> con = x -> System.out.println(x);
        con.accept("厉sxxx害");
    }

    @Test
    public void test4(){
        Comparator<Integer> com = (x,y) -> {
            System.out.println("函数接口");
            return Integer.compare(x,y);
        };
        System.out.println(com);
    }

    @Test
    public void test5(){
        Comparator<Integer> com = (x,y) -> Integer.compare(x,y);
        System.out.println(com);
    }

    @Test
    public void test6(){
        Comparator<Integer> com = (x,y) -> Integer.compare(x,y);
        System.out.println(com);
    }
}
