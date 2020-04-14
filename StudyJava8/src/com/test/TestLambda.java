package com.test;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.*;

/**
 * @author WXJ
 * @descrption
 * @create 2020/4/14 16:21
 **/
public class TestLambda {

    //匿名内部类
    @Test
    public void test1(){
        Comparator<Integer> com = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1,o2);
            }
        };
        TreeSet<Integer> ts = new TreeSet<>(com);
    }

    //Lambda
    @Test
    public void test2(){
        Comparator<Integer> com = (x,y) -> Integer.compare(x,y);
        TreeSet<Integer> ts = new TreeSet<>(com);
    }

    //需求：获取当前公司中员工年龄大于35的员工信息
    List<Employee> employees = Arrays.asList(
            new Employee("1111",18,1111.99),
            new Employee("2222",38,2222.99),
            new Employee("3333",50,3333.99),
            new Employee("4444",16,4444.99),
            new Employee("5555",8,5555.99),
            new Employee("6666",11,6666.99),
            new Employee("7777",13,7777.99)
    );

    @Test
    public void test3(){
        List<Employee> list = filterEmployees(employees);
        for (Employee employee : list){
            System.out.println(employee.getName());
        }
    }

    public List<Employee> filterEmployees(List<Employee> list){
        List<Employee> employees = new ArrayList<>();

        for (Employee employee: list){
            if (employee.getAge() >= 35){
                employees.add(employee);
            }
        }
        return employees;
    }

    //需求：获取当前公司员工大于5000的员工信息
    public List<Employee> filterEmployees2(List<Employee> list){
        List<Employee> employees = new ArrayList<>();

        for (Employee employee: list){
            if (employee.getSalary() >= 5000){
                employees.add(employee);
            }
        }
        return employees;
    }

    //优化方式一：策略设计模式
    @Test
    public void test4(){
        List<Employee> list = filterEmployeeByInterface(employees,new FilterEmployeeByAge());
        for (Employee employee : list){
            System.out.println(employee.toString());
        }

        System.out.println("----------------------");
        List<Employee> list2 = filterEmployeeByInterface(employees,new FilterEmployeeBySalary());
        for (Employee employee : list2){
            System.out.println(employee.toString());
        }
    }

    public List<Employee> filterEmployeeByInterface(List<Employee> list,MyPredicate<Employee> mp){
        List<Employee> employees = new ArrayList<>();
        for (Employee employee: list){
            if (mp.test(employee)){
                employees.add(employee);
            }
        }
        return employees;
    }

    public List<Employee> filterEmployeeByInterfaceSalary(List<Employee> list,MyPredicate<Employee> mp){
        List<Employee> employees = new ArrayList<>();
        for (Employee employee: list){
            if (mp.test(employee)){
                employees.add(employee);
            }
        }
        return employees;
    }

    //优化二：匿名内部类
    @Test
    public void test5(){
        List<Employee> list2 = filterEmployeeByInterface(employees,new MyPredicate<Employee>(){
            @Override
            public boolean test(Employee employee) {
                return employee.getSalary() <= 5000;
            }
        });

        System.out.println("----------------------");
        for (Employee employee : list2){
            System.out.println(employee.toString());
        }
    }


    //优化方式3：Lambda
    @Test
    public void test6(){
        List<Employee> list2 = filterEmployeeByInterface(employees, (e) -> e.getSalary() <= 5000);

        System.out.println("----------------------");
        for (Employee employee : list2){
            System.out.println(employee.toString());
        }
    }

    //优化方式4： Stream API
    @Test
    public void test7(){
        employees.stream()
                .filter((e) -> (e.getSalary()) >= 5000)
                .limit(2)
                .forEach(System.out::println);

        employees.stream()
                .map(Employee::getName)
                .forEach(System.out::println);
    }
}
