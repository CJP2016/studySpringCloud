package com.test;

@FunctionalInterface
public interface MyPredicate<T> {
    public boolean test(T t);
}