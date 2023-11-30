package com.philyeo.lotteryapp.shared;

@FunctionalInterface
public interface TriFunction<A, B, C, O> {
    O apply(A a, B b, C c);

    default <R> TriFunction<A, B, C, O> andThen(TriFunction<A, B, C, O> after) {
        return (A a, B b, C c) -> after.apply(a,b,c);
    }
}
