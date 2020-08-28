package com.kevin.chapter13;

import java.util.Arrays;

/**
 * @author kevin chen
 */
// 可以强制要求接口只有一个抽象方法
@FunctionalInterface
interface Functional {
    String goodbye(String arg);
}

interface FunctionalNoAnn {
    String goodbye(String arg);
}

class FunctionalNoAnnImpl implements FunctionalNoAnn {

    @Override
    public String goodbye(String arg) {
        System.out.println("FunctionalNoAnnImpl");
        return null;
    }
}

/*
@FunctionalInterface
interface NotFunctional {
  String goodbye(String arg);
  String hello(String arg);
}
产生错误信息:
NotFunctional is not a functional interface
multiple non-overriding abstract methods
found in interface NotFunctional
*/

public class FunctionalAnnotation {
    public String goodbye(String arg) {
        System.out.println("FunctionalAnnotation goodbye");
        return "Goodbye, " + arg;
    }

    public static void main(String[] args) {
        FunctionalAnnotation fa = new FunctionalAnnotation();
        Functional f = fa::goodbye;
        FunctionalNoAnn fna = fa::goodbye;
        Runnable r = fa::test;// 方法引用返回了一个函数式接口实例
        r.run();
        // Functional fac = fa; // Incompatible
        Functional fl = a -> "Goodbye, " + a;
        FunctionalNoAnn functional = a -> a + "";
    }

    public void test() {
        System.out.println("method reference test");
    }
}
