package com.optional;

/**
 * @author WXJ
 * @descrption
 * @create 2020/4/20 15:28
 **/
public class Man {
    private Goodness godness;

    public Man(){}

    public Man(Goodness godness){
        this.godness = godness;
    }

    public Goodness getGodness() {
        return godness;
    }

    public void setGodness(Goodness godness) {
        this.godness = godness;
    }

    @Override
    public String toString() {
        return "Man{" +
                "godness=" + godness +
                '}';
    }
}
