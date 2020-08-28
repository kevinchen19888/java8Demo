package com.kevin.chapter13;

/**
 * Fibonacci 序列递归实现
 *
 * @author kevin chen
 */
public class RecursiveFibonacci {
    IntCall fib;

    RecursiveFibonacci() {
        fib = n -> n == 0 ? 0 : n == 1 ? 1 : fib.call(n - 1) + fib.call(n - 2);
    }

    int fibonacci(int n) {
        return fib.call(n);
    }

    public static void main(String[] args) {
        RecursiveFibonacci rf = new RecursiveFibonacci();
        for (int i = 0; i <= 10; i++)
            System.out.println(rf.fibonacci(i));
    }
}
