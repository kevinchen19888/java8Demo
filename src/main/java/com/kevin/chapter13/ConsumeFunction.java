package com.kevin.chapter13;

import java.util.function.Function;

/**
 * @author kevin chen
 */
class One {
}

class Two {
}

public class ConsumeFunction {
    // 高阶函数: 生产或消费函数的函数
    static Two consume(Function<One, Two> function) {
        return function.apply(new One());
    }

    public static void main(String[] args) {
        Two two = consume(one -> new Two());
    }
}
