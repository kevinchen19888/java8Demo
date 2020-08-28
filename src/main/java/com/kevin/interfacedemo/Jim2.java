package com.kevin.interfacedemo;

/**
 * @author kevin chen
 */
public interface Jim2 {

    default void jim() {
        System.out.println("Jim2::jim");
    }
}
