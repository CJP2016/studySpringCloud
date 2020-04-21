package com.optional;

import java.util.Optional;

/**
 * @author WXJ
 * @descrption
 * @create 2020/4/20 15:34
 **/
public class NewMan {
    private Optional<Goodness> godness = Optional.empty();

    public NewMan(){}
    public NewMan(Optional<Goodness>godness){
        this.godness = godness;
    }

    public Optional<Goodness> getGodness() {
        return godness;
    }

    public void setGodness(Optional<Goodness> godness) {
        this.godness = godness;
    }

    @Override
    public String toString() {
        return "NewMan{" +
                "godness=" + godness +
                '}';
    }
}
