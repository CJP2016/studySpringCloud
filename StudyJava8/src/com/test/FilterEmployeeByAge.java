package com.test;

/**
 * @author WXJ
 * @descrption
 * @create 2020/4/14 16:38
 **/
public class FilterEmployeeByAge implements MyPredicate<Employee> {
    @Override
    public boolean test(Employee employee) {
        return employee.getAge() >= 35;
    }
}
