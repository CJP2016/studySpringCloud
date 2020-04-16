package com.exer;

import com.test.Employee;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author WXJ
 * @descrption
 * @create 2020/4/15 16:41
 **/
public class TestLambda {

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

    @Test
    public void test1(){
        Collections.sort(employees,(e1,e2) -> {
            if (e1.getAge() == e2.getAge()){
                return e1.getName().compareTo(e2.getName());
            }else {
                return -Integer.compare(e1.getAge(),e2.getAge());
            }
        });

        for (Employee employee:employees){
            System.out.println(employee);
        }
    }

    @Test
    public void test2(){
        String x = strHandler("\t\t\t111111111",(str -> str.trim()));
        System.out.println(x);
         x = strHandler("asdasd",(str -> str.toUpperCase()));
        System.out.println(x);
    }

    public String strHandler(String str,MyFunction myFunction){
        return myFunction.getValue(str);
    }

    @Test
    public void test3(){
        op(100L,200L,(x,y) -> x + y);
        op(100L,200L,(x,y) -> x * y);

    }

    public void op(Long l1,Long l2,MyFunction2<Long,Long> mf){
        System.out.println(mf.getValue(l1,l2));
    }
}
