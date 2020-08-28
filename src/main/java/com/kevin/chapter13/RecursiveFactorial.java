package com.kevin.chapter13;

/**
 * 递归lambda表达式
 * 递归方法必须是实例变量或静态变量，否则会出现编译时错误
 *
 * @author kevin chen
 */
public class RecursiveFactorial {
    static IntCall fact;

    public static void main(String[] args) {
        fact = n -> n == 0 ? 1 : n * fact.call(n - 1);
        for (int i = 1; i < 10; i++) {
            System.out.println(fact.call(i));
        }

    }


}

interface IntCall {
    int call(int arg);
}
