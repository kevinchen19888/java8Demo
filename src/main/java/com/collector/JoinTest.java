package com.collector;

public class JoinTest {
    public static void main(String[] args) {
        System.out.println("main start");

        Thread t1 = new Thread(new Worker("thread-1"));
        Thread t2 = new Thread(new Worker("thread-2"));

        try {
            t1.start();
            t2.start();

            t2.join();
            t1.join();//当前线程等待被加入线程执行完成再执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("main end");
    }
}

class Worker implements Runnable {

    private String name;

    public Worker(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name);
        }
    }

}

