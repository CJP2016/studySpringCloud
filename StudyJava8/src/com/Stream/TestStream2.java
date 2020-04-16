package com.Stream;

import com.test.Employee;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author WXJ
 * @descrption
 * 三个步骤
 *  1.创建Stream（数据源（集合、数组等）转换成流）
 *      ↓
 *  2.中间操作（一系列流水线式的中间操作，对数据源的数据进行处理） Filter、map
 *      ↓
 *  3.终止操作（产生一个新流）
 *
 * @create 2020/4/16 15:00
 **/
public class TestStream2 {

    List<Employee> employees = Arrays.asList(
            new Employee(1,"1111",18,11111.11, Employee.Status.FREE),
            new Employee(2,"2222",38,22222.22, Employee.Status.FREE),
            new Employee(3,"3333",50,3333.99, Employee.Status.FREE),
            new Employee(4,"4444",16,4444.99, Employee.Status.FREE),
            new Employee(5,"5555",8,5555.99, Employee.Status.FREE),
            new Employee(6,"6666",11,6666.99, Employee.Status.FREE),
            new Employee(5,"5555",8,5555.99, Employee.Status.FREE),
            new Employee(6,"6666",11,6666.99, Employee.Status.FREE),
            new Employee(7,"7777",13,7777.99, Employee.Status.FREE)
    );

    //中间操作
    /*
    * 筛选与切片
    *   filter——接收Lambda，从流中排除某些元素
    *   limit——截断流，使其元素不超过给定数量。
    *   skip(n)——跳过元素，返回一个扔掉了前N个元素的流。若流中元素不足n个，则返回一个空流。与limit（n）互补
    *   distinct——筛选，通过流所生成的元素的hashCode() 和 equals() 去除重复元素
    * */

    //内部迭代：跌代操作由Stream API完成
    @Test
    public void test1(){
        //中间操作----不会执行任何的处理
        Stream<Employee> stream =employees.stream()
                 .filter((e) -> e.getAge() > 35);

        //终止操作---一次性全部内容的处理，即“惰性求值”
        stream.forEach(System.out::println);
    }

    //外部迭代
    @Test
    public void  test2(){
        Iterator<Employee> iterable = employees.iterator();
        while (iterable.hasNext()){
            System.out.println(iterable.next());
        }
    }

    //
    @Test
    public void  test3(){
        employees.stream()
                 .filter(e -> {
                     System.out.println("短路！");
                     return e.getSalary() > 5000;
                 })
                 .limit(2)
                 .forEach(System.out::println);
    }

    //distinct 需要在类里重写hashcode 和 equals
    @Test
    public void test4(){
        employees.stream()
                 .filter( e -> e.getSalary() > 5000)
                 .skip(2)
                 .distinct()
                 .forEach(System.out::println);
    }


    /*
    *   映射
    *   map——接收Lambda，将元素转换成其他形式或提取信息，接收一个函数作为参数，该参数会被应用到每个元素上，并将其映射成一个新的元素。
    *   flatMap —— 接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流
    * */
    @Test
    public void test5(){
        List<String> list = Arrays.asList("aaa","vvv","ccc","ddd","eee");

        list.stream()
                .map( str -> str.toUpperCase())
                .forEach(System.out::println);

        System.out.println("------------------");

        employees.stream()
                .map(Employee::getName)
                .distinct()
                .forEach(System.out::println);

        System.out.println("Map------------------返回流");

        Stream<Stream<Character>> steamStream= list.stream()
                .map(TestStream2::filterCharacter);//{{a,a,a},{b,b,b}}
        steamStream.forEach( sm -> {sm.forEach(System.out::println);});

        System.out.println("FlatMap------------------返回流里的数据");

        Stream<Character> stream= list.stream()
                .flatMap(TestStream2::filterCharacter); //{a,a,a,,b,b,b}
        stream.forEach(System.out::println);
    }

    public static Stream<Character> filterCharacter(String str){
        List<Character> list = new ArrayList<>();

        for (Character ch : str.toCharArray()){
            list.add(ch);
        }
        return list.stream();
    }

    /*
    * 排序
    *   sorted() —— 自然排序(Comparable)
    *   sorted(Comparator com) ————定制排序(Comparator)
    * */
    @Test
    public void test6(){
        List<String> list = Arrays.asList("aaa","vvv","ccc","ddd","eee");
        list.stream()
                .sorted()
        .forEach(System.out::println);

        System.out.println("------------------------");

        employees.stream()
                .sorted((e1,e2) -> {
                    if (e1.getAge() == (e2.getAge())){
                        return e1.getName().compareTo(e2.getName());
                    }else {
                        return -Integer.compare(e1.getAge(),e2.getAge());
                    }
                }).forEach(System.out::println);
    }

}
