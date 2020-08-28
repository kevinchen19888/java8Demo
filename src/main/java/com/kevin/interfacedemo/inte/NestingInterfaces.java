package com.kevin.interfacedemo.inte;

/**
 * @author kevin chen
 */
public class NestingInterfaces {
    public class BImp implements A.B {
        @Override
        public void f() {}
    }

    class CImp implements A.C {
        @Override
        public void f() {}
    }
    // Cannot implements a private interface except
    // within that interface's defining class:
    //- class DImp implements A.D {
    //- public void f() {}
    //- }


    public static void main(String[] args) {
         //Can't access to A.D:
        A a = new A();
        //Doesn't return anything but A.D:
        //- A.DImp2 di2 = a.getD();
        // cannot access a member of the interface:
        //- a.getD().f();
    }
}
