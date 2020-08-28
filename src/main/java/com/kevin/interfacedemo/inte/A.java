package com.kevin.interfacedemo.inte;

/**
 * 恰当的原则是优先使用类而不是接口。从类开始，
 * 如果使用接口的必要性变得很明确，那么就重构。接口是一个伟大的工具，但它们容易被滥用。
 *
 * @author kevin chen
 */
class A {

    interface B {
        void f();
    }


    public class BImp implements B {
        @Override
        public void f() {
        }
    }


    public interface C {
        void f();
    }

    class CImp implements C {
        @Override
        public void f() {
        }
    }


    private class CImp2 implements C {
        @Override
        public void f() {
        }
    }


    //private 接口不能在定义它的类之外被实现
    // 实现类只能在定义它的类之内使用
    private interface D {
        void f();
    }

    private class DImp implements D {
        @Override
        public void f() {
        }
    }

    public class DImp2 implements D {
        @Override
        public void f() {
        }
    }

    public D getD() {
        return new DImp2();
    }
}
