package com.optional;

import com.test.Employee;
import org.junit.Test;

import java.util.Optional;

/**
 * @author WXJ
 * @descrption
 * @create 2020/4/20 15:11
 **/
public class TestOptional {

    /*
    * Optional 容器类的常用方法：
    *   Optional.of(T t) : 创建一个Optional实例
    *   Optional.empty() : 创建一个空的Optional 实例
    *   Optional.ofNullable(T t) :若t 不为null，创建Optional 实例，否则创建空实例
    *   isPresent() : 判断是否包含值
    *   orElse(T t) : 如果调用对象包含值，返回该值，否则返回t
    *   orElseGet(Supplier s) : 如果调用对象包含值，返回该值，否则返回s获取的值
    *   map(Function f) : 如果有值就对其处理，并返回处理后的Optional，否则返回Optional.empty()
    *   flatMap(Function mapper) : 与map类似，要求返回值必须是Optional
    * */

    @Test
    public void test1(){
        Optional<Employee> op= Optional.of(new Employee());
//        Optional<Employee> op= Optional.of(null); //报错
        System.out.println(op.get());
    }

    @Test
    public void test2(){
        Optional<Employee> op = Optional.empty();
        System.out.println(op.get());
    }

    @Test
    public void test3(){
//        Optional<Employee> op=Optional.ofNullable(new Employee());
        Optional<Employee> op=Optional.ofNullable(null);
        if (op.isPresent())
            System.out.println(op.get());

//       Employee emp = op.orElse(new Employee(1,"sss",11,111, Employee.Status.FREE));
//        System.out.println(emp);

       Employee emp = op.orElseGet( () -> new Employee());
        System.out.println(emp);

    }

    @Test
    public void test4(){
       Optional<Employee> op =  Optional.ofNullable(new Employee(1,"sss",11,111, Employee.Status.FREE));
//       Optional<String> str = op.map((e) -> e.getName());
//       System.out.println(str.get());

        Optional<String> str2 = op.flatMap((e) -> Optional.of(e.getName()));
        System.out.println(str2.get());
    }

    @Test
    public void test5(){
//        Man man = new Man();
//        String n = getGodnessName(man);
//        System.out.println(n);

        Optional<Goodness> gn = Optional.ofNullable(new Goodness("默认值3"));
        Optional<NewMan> op = Optional.ofNullable(new NewMan());
        String n = getGodnessName2(op);
        System.out.println(n);
    }

    //需求：获取一个男人心中女神的名字
    public String getGodnessName(Man man){
        if (man != null) {
            Goodness gn = man.getGodness();
            if (gn != null)
                return gn.getName();
        }

        return "默认值";
    }

    public String getGodnessName2(Optional<NewMan> man){
        return man.orElse(new NewMan())
                  .getGodness()
                  .orElse(new Goodness("默认值2"))
                  .getName();
    }

}
