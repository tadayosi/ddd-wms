package com.googlecode.dddwms.util;

public abstract class AbstractPredicate<T> implements Predicate<T> {

    @Override
    public Predicate<T> and(Predicate<T> left) {
        return new AndPredicate<T>(left, this);
    }

}
