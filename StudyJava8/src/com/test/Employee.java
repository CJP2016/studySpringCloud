package com.test;

/**
 * @author WXJ
 * @descrption
 * @create 2020/4/14 16:28
 **/
public class Employee {

    private String name;
    private int age;
    private double salary;
    public Employee(){
        super();
    }

    public Employee(String s, int i, double v) {
        this.name = s;
        this.age = i;
        this.salary = v;
    }

    public double getSalary() {
        return salary;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                '}';
    }
}
