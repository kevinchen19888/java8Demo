package com.kevin.chapter13;

/**
 * @author kevin chen
 */
@FunctionalInterface
interface TriFunction<T, U, V, R> {

    R apply(T t, U u, V v);
}

public class ThreeParamsFI {
    static int f(int i, long l, double d) {
        return 99;
    }

    public static void main(String[] args) {
        TriFunction t = (a, b, c) -> String.format("%s|%s|%s", a, b, c);
        System.out.println(t.apply("kevin", 10, 100));

        TriFunction<Integer, Long, Double, Integer> tf = ThreeParamsFI::f;
        tf = (i, l, d) -> 12;
        System.out.println(tf.apply(10, 10L, 100.0));;
    }
}


