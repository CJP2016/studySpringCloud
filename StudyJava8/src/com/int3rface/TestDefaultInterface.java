package com.int3rface;

/**
 * @author WXJ
 * @descrption
 * @create 2020/4/20 15:44
 **/
public class TestDefaultInterface {
    public static void main(String[] args) {
        //接口默认方法的“类优先”原则
        /*
        * 若在一个接口中定义了一个默认方法，而另一个父类或接口中又定义了一个同名的方法是
        *   选择父类中的方法，如果父类提供具体的实现，那么接口中具有相同名称和参数的默认方法会被忽略
        *   接口冲突。如果一个父接口提供一个默认方法，而另一个接口也提供了一个具有相同名称和参数列表的方法（不管方法是否是默认方法，那么必须覆盖该方法来解决冲突）
        * */
        SubClass sc = new SubClass();
        System.out.println(sc.getName());
        MyInterface.show();
    }
}
