package com.kevin.chapter13;

/**
 * 捕获构造函数的引用，然后通过引用调用该构造函数。
 *
 * @author kevin chen
 */
class Dog {
    String name;
    int age = -1; // For "unknown"

    Dog() {
        name = "stray";
    }

    Dog(String nm) {
        name = nm;
    }

    Dog(String nm, int yrs) {
        name = nm;
        age = yrs;
    }
}

interface MakeNoArgs {
    Dog make();
}

interface Make1Arg {
    Dog make(String nm);
}

interface Make2Args {
    Dog make(String nm, int age);
}

interface Make3Args {
    Dog make(String nm,String lm, int age);
}


public class CtorReference {
    public static void main(String[] args) {
        MakeNoArgs mna = Dog::new; // [1]
        Make1Arg m1a = Dog::new;   // [2]
        Make2Args m2a = Dog::new;  // [3]
        //Make3Args m3a = Dog::new;  // 编译出错

        Dog dn = mna.make();
        Dog d1 = m1a.make("Comet");
        Dog d2 = m2a.make("Ralph", 4);
    }
}
