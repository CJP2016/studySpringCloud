package com.optional;

/**
 * @author WXJ
 * @descrption
 * @create 2020/4/20 15:28
 **/
public class Goodness {
    private String name;

    public Goodness(){}

    public Goodness(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Goodness{" +
                "name='" + name + '\'' +
                '}';
    }
}
