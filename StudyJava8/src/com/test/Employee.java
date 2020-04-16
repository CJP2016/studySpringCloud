package com.test;

import java.util.Objects;

/**
 * @author WXJ
 * @descrption
 * @create 2020/4/14 16:28
 **/
public class Employee {

    private int id;
    private String name;
    private int age;
    private double salary;
    private Status status;
    public Employee(){
        super();
    }

    public Employee(int i){
        this.id = i;
    }

    public Employee(int i,int age){
        this.id = i;
        this.age = age;
    }

    public Employee(int id,String s, int i, double v,Status status) {
        this.id = id;
        this.name = s;
        this.age = i;
        this.salary = v;
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id &&
                age == employee.age &&
                Double.compare(employee.salary, salary) == 0 &&
                Objects.equals(name, employee.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, salary);
    }

    public enum Status{
        FREE,
        BUSY,
        VOCATION;
    }
}
