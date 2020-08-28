package com.kevin.chapter13;

/**
 * @author kevin chen
 */
class Go {
    static void go() {
        System.out.println("Go::go()");
    }
}

public class RunnableMethodReference {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            public void run() {
                System.out.println("Anonymous");
            }
        }).start();

        new Thread(
                () -> System.out.println("lambda") // lambda表达式方式
        ).start();

        new Thread(Go::go).start(); // 方法引用方式

        new Thread(() -> System.out.println("thread lambda")).start();
    }
}
