package com.int3rface;

/**
 * @author WXJ
 * @descrption
 * @create 2020/4/20 15:43
 **/
public class SubClass /*extends MyClass*/ implements MyFun,MyInterface{

    @Override
    public String getName() {
        return MyFun.super.getName();
    }
}
