package com.kevin.interfacedemo.staticmethod;

/**
 * 接口静态方法使用示例
 *
 * @author kevin chen
 */
public class Client {
    public static void main(String[] args) {
        Operations.runOps(new Bing(), new Crack());
    }

}
