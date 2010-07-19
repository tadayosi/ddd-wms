package com.googlecode.dddwms.util;

public interface Predicate<T> {
    boolean apply(T element);
    Predicate<T> and(Predicate<T> left);
}
