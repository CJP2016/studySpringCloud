package com.testannotation;

import com.sun.istack.internal.NotNull;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * @author WXJ
 * @descrption
 *  重复注解与类型注解
 *
 *
 * @create 2020/4/21 15:07
 **/
public class TestAnnotation {

    private  Object obj = null;

    @Test
    public void test1() throws NoSuchMethodException {
        //利用反射调用方法
        Class<TestAnnotation> clazz = TestAnnotation.class;

        Method m1 = clazz.getMethod("show");
        MyAnnotation[] mas = m1.getAnnotationsByType(MyAnnotation.class);
        for (MyAnnotation myAnnotation : mas){
            System.out.println(myAnnotation.value());
        }


    }

    @MyAnnotation("Hello")
    @MyAnnotation("World")
//    public void show(@MyAnnotation("abc") String string){
    public void show(){

    }
}
