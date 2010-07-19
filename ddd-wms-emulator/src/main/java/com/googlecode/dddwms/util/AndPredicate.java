package com.googlecode.dddwms.util;

public class AndPredicate<T> extends AbstractPredicate<T> {

    private Predicate<T> left;
    private Predicate<T> right;
    
    public AndPredicate(Predicate<T> right, Predicate<T> left) {
        this.right = right;
        this.left = left;
    }
    
    @Override
    public boolean apply(T element) {
        return left.apply(element) && right.apply(element);
    }

}
