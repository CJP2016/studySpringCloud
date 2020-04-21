package com.int3rface;

public interface MyInterface {
    default String getName(){
        return "keke";
    }

    public static void show(){
        System.out.println("接口中的静态方法");
    }
}
