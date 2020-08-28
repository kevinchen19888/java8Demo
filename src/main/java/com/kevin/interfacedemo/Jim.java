package com.kevin.interfacedemo;

/**
 * @author kevin chen
 */
public class Jim implements cc.mrbird.defaultinterface.Jim1, Jim2 {
    @Override
    public void jim() {
        Jim2.super.jim();// 当父接口的默认方法有相同的方法签名时,需要重写此默认方法;
    }


    public static void main(String[] args) {
        Jim jim = new Jim();
        jim.jim();
    }
}
