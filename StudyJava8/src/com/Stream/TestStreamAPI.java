package com.Stream;

import com.test.Employee;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author WXJ
 * @descrption
 * @create 2020/4/16 16:28
 **/
public class TestStreamAPI {

    List<Employee> employees = Arrays.asList(
            new Employee(1,"1111",18,11111.11, Employee.Status.FREE),
            new Employee(2,"2222",38,22222.22, Employee.Status.BUSY),
            new Employee(3,"3333",50,3333.99, Employee.Status.VOCATION),
            new Employee(4,"4444",16,4444.99, Employee.Status.FREE),
            new Employee(5,"5555",8,5555.99, Employee.Status.BUSY)
    );

    @Test
    public void test1(){
//        List<Integer> list = Arrays.asList(1,2,3,4,5);
//        list.stream().map(e -> e*e).forEach(System.out::println);
          Integer[] nums = new Integer[]{1,2,3,4,5};
          Arrays.stream(nums)
                  .map(e -> e*e).forEach(System.out::println);
    }

    @Test
    public void test2(){
       Optional<Integer> count=  employees.stream()
                .map(e -> 1)
                .reduce(Integer::sum);
        System.out.println(count);
    }

}

