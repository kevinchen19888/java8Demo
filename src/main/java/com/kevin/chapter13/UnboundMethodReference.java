package com.kevin.chapter13;

/**
 * @author kevin chen
 */
// 没有方法引用的对象
class X {
    String f() {
        return "X::f()";
    }
}

interface MakeString {
    String make();
}

interface TransformX {
    String transform(X x);
}

public class UnboundMethodReference {
    public static void main(String[] args) {
        //MakeString ms = X::f; // [1]
        TransformX sp = X::f;// 未绑定的方法引用,实际上包含一个隐藏的参数:this
        X x = new X();
        System.out.println(sp.transform(x)); // [2]
        System.out.println(x.f()); // 同等效果
    }
}
