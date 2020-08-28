package com.kevin.chapter13;

import java.util.function.Function;

/**
 * @author kevin chen
 */

class I {
    @Override
    public String toString() {
        return "I";
    }
}

class O {
    @Override
    public String toString() {
        return "O";
    }
}

public class TransformFunction {
    static Function<I, O> transform(Function<I, O> function) {
        return function.andThen(o -> {
            System.out.println(o);
            return new O();
        });
    }



    public static void main(String[] args) {
        Function<I, O> f2 = transform(i -> {
            System.out.println(i);
            return new O();
        });
        O o = f2.apply(new I());
    }
}