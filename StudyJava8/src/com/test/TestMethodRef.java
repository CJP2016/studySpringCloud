package com.test;

import org.junit.Test;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.function.*;

/**
 * @author WXJ
 * @descrption
 * 方法引用：若 Lambda 体中的内容有方法以及实现，我们可以使用“方法引用”
 *      可以理解为方法引用是Lambda表达式的另外一种表达形式
 *
 * 主要有三种语法形式：
 *      对象::实例方法名
 *      类::静态方法名
 *      类::实例方法名
 *
 *  注意：
 *      1.Lambda 体中调用方法的参数列表与返回值类型，要与函数式接口中抽象方法的函数列表和返回值类型保持一致
 *      2.若Lambda参数列表中的第一个参数是实例方法的调用者，第二个参数是实例方法的参数时，可以使用ClassName::method
 *
 * 二、构造器引用：
 *
 * 格式：
 *     ClassName::new
 *
 * 注意：需要调用的构造器的参数列表要与函数式接口中抽象方法的参数列表保持一致
 *
 * 三、 数组引用：
 *  Type::new;
 *
 * @create 2020/4/16 14:17
 **/
public class TestMethodRef {

    //对象::实例方法名
    @Test
    public void test1(){
        PrintStream printStream = System.out;
        Consumer<String> con = x -> printStream.println(x);


        Consumer<String> consumer = printStream::println;
        Consumer<String> consumer2 = System.out::println;
        consumer2.accept("adadada");
    }

    //类::静态方法名
    @Test
    public void  test2(){
        Comparator<Integer> comparator = (x,y) -> Integer.compare(x,y);

        Comparator<Integer> comparator1 = Integer::compare;


    }

    //类::实例方法名
    @Test
    public void test4(){
        BiPredicate<String,String> bp = (x,y) -> x.equals(y);
        //第一个参数是调用者，第二个参数是实例方法的参数时，可以使用ClassName::method
        BiPredicate<String,String> bp2 = String::equals;
    }

    //构造器引用
    @Test
    public void test5(){
        Supplier<Employee> supplier = () -> new Employee();

        //构造器引用方式
        Supplier<Employee> supplier1 = Employee::new;
        Employee employee = supplier1.get();
        System.out.println(employee.toString());
    }

    //带参数构造器
    @Test
    public void test6(){
        Function<Integer,Employee> function = (x) -> new Employee(1);

        Function<Integer,Employee> function2 = Employee::new;
        Employee employee= function2.apply(1);
        System.out.println(employee);

        BiFunction<Integer,Integer,Employee> biFunction = Employee::new;

    }

    //数组引用
    @Test
    public void test7(){
        Function<Integer,String[]> function = (x) -> new String[x];
        String[] strings = function.apply(10);
        System.out.println(strings.length);

        Function<Integer,String[]> function2 = String[]::new;
        String[] strings2 = function2.apply(20);
        System.out.println(strings2.length);
    }


    //
    @Test
    public void test3(){
        Employee employee = new Employee(1,"11111",11,11, Employee.Status.FREE);
        Supplier<String> sup = () -> employee.getName();
        String str = sup.get();
        System.out.println(str);

        Supplier<Integer> sup2 = employee::getAge;
        Integer num = sup2.get();
        System.out.println(num);

    }
}
