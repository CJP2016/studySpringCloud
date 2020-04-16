package com.Stream;

import com.test.Employee;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author WXJ
 * @descrption
 * 终止操作
 *
 * @create 2020/4/16 15:41
 **/
public class TestStream3 {

    List<Employee> employees = Arrays.asList(
            new Employee(1,"1111",18,11111.11, Employee.Status.FREE),
            new Employee(2,"2222",38,22222.22, Employee.Status.BUSY),
            new Employee(3,"3333",50,3333.99, Employee.Status.VOCATION),
            new Employee(4,"4444",16,4444.99, Employee.Status.FREE),
            new Employee(5,"5555",8,5555.99, Employee.Status.BUSY)
    );

    /*
    * 查找与匹配
    *   allMatch ————检查是否匹配所有元素
    *   anyMatch ————检查是否至少匹配一个元素
    *   noneMatch ————检查是否没有匹配所有元素
    *   findFirst ————返回第一个元素
    *   findAny ————返回当前流中的任意元素
    *   count ————返回流中元素的总个数
    *   max ————返回流中最大值
    *   mix ————返回流中最小值
    *
    * */

    @Test
    public void test1(){
       boolean b= employees.stream()
               .allMatch( e -> e.getStatus().equals(Employee.Status.BUSY));
        System.out.println(b);

        boolean b1= employees.stream()
                .anyMatch( e -> e.getStatus().equals(Employee.Status.BUSY));
        System.out.println(b1);

        boolean b2 = employees.stream()
                .noneMatch( e -> e.getStatus().equals(Employee.Status.BUSY));
        System.out.println(b2);

        //容器类Optional
        Optional<Employee> op = employees.stream()
                .sorted((e1,e2) -> Double.compare(e1.getSalary(),e2.getSalary()))
                .findFirst();
        System.out.println(op.get());

        //parallelStream 随机显示符合过滤的结果 1111或4444  ，stream只会显示在前面的那个数据 1111
//        Optional<Employee> op2= employees.stream()
        Optional<Employee> op2= employees.parallelStream()
                 .filter( e -> e.getStatus().equals(Employee.Status.FREE))
                .findAny();
        System.out.println(op2.get());


    }

    @Test
    public void test2(){
        Long count = employees.stream()
                .count();
        System.out.println(count);

        Optional<Employee> op1= employees.stream().max((e1,e2) -> Double.compare(e1.getSalary(),e2.getSalary()));
        System.out.println(op1.get());

        //先把工资提取出来，再比较匹配
        Optional<Double> op2= employees.stream()
                                        .map(Employee::getSalary)
                                        .min(Double::compareTo);
        System.out.println(op2.get());
    }

    /*
    * 规约
    *   reduce(T indentity,BinaryOperator) / reduce(BinaryOperator) ————可以将流中元素反复结合起来，得到一个值。
    *
    * */
    @Test
    public void test3(){
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        int sum = list.stream()
                .reduce(0,(x,y) -> x + y);
        System.out.println(sum);
        System.out.println("--------------------------");

        Optional<Double> op = employees.stream()
                .map(Employee::getSalary)
                .reduce(Double::sum);
        System.out.println(op.  get());
    }

    /*
    * 收集
    *   collect ————将流转换为其他形式，接收一个Collector接口的实现，用于给Stream中元素做汇总的方法
    * */
    @Test
    public void test4(){
        List<String> list = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.toList());
        list.forEach(System.out::println);

        System.out.println("----------------");
        Set<String> set = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.toSet());
        set.forEach(System.out::println);

        System.out.println("----------------");
        Collection<String> collect = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.toCollection(HashSet::new));
        collect.forEach(System.out::println);
    }

    @Test
    public void test5(){
        //总数
        Long count = employees.stream()
                .collect(Collectors.counting());
        System.out.println(count);

        System.out.println("平均值-------------------");
        //平均值
        Double avg =employees.stream()
                .collect(Collectors.averagingDouble(Employee::getSalary));
        System.out.println(avg);

        System.out.println("和-------------------");
        Double sum =  employees.stream()
                .collect(Collectors.summingDouble(Employee::getSalary));
        System.out.println(sum);

        System.out.println("最大值------------------");
        Optional<Employee> max =  employees.stream()
                .collect(Collectors.maxBy((e1,e2) -> Double.compare(e1.getSalary(),e2.getSalary())));
        System.out.println(max.get());

        System.out.println("最小值------------------");

        Optional<Double> min= employees.stream()
                .map(Employee::getSalary)
                .collect(Collectors.minBy(Double::compareTo));
        System.out.println(min.get());

    }

    //分组
    @Test
    public void test6(){
        Map<Employee.Status,List<Employee>> map = employees.stream()
                .collect(Collectors.groupingBy(Employee::getStatus));
        System.out.println(map);
    }

    //多级分组
    @Test
    public void test7(){
        Map<Employee.Status,Map<String,List<Employee>>> map= employees.stream()
                .collect(Collectors.groupingBy(Employee::getStatus,Collectors.groupingBy(e -> {
                    if (e.getAge() <= 35){
                        return "青年";
                    }else if (e.getAge() <= 50){
                        return "中年";
                    }else{
                        return "老年";
                    }
                })));
        System.out.println(map);
    }

    //分区
    @Test
    public void test8(){
        Map<Boolean,List<Employee>> map= employees.stream()
                .collect(Collectors.partitioningBy(e -> e.getSalary() > 8000));
        System.out.println(map);

    }

    //总数、总和、最小、最大、平均值
    @Test
    public void  test9(){
        DoubleSummaryStatistics dss= employees.stream().collect(Collectors.summarizingDouble(Employee::getSalary));
        System.out.println(dss);
        System.out.println(dss.getSum());
        System.out.println(dss.getCount());
        System.out.println(dss.getAverage());
        System.out.println(dss.getMin());
        System.out.println(dss.getMax());
    }

    @Test
    public void test10(){

       String str =  employees.stream()
                .map(Employee::getName)
//                .collect(Collectors.joining(","));
                .collect(Collectors.joining(",","---","==="));
        System.out.println(str);
    }
}
