package com.kevin.interfacedemo.staticmethod;

/**
 * @author kevin chen
 */
public interface Operations {
    void execute();

    /**
     * 这是模版方法设计模式的一个版本，runOps() 是一个模版方法。runOps() 使用可变参数列表，
     * 因而我们可以传入任意多的 Operation 参数并按顺序运行它们
     *
     */
    static void runOps(Operations... ops) {
        for (Operations op : ops) {
            op.execute();
        }
    }

    static void show(String msg) {
        System.out.println(msg);
    }
}
