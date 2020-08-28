package com.kevin.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author kevin chen
 */
public class ReversibleArrayList<T> extends ArrayList<T> {
    ReversibleArrayList(Collection<T> c) {
        super(c);
    }

    public Iterable<T> reversed() {
        return () -> new Iterator<T>() {
            int current = size() - 1;

            public boolean hasNext() {
                return current > -1;
            }

            public T next() {
                return get(current--);
            }

            public void remove() { // Not implemented
                throw new UnsupportedOperationException();
            }
        };
    }

    public static void main(String[] args) {
        ReversibleArrayList<Integer> list = new ReversibleArrayList<>(new ArrayList<>());
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        for (Integer integer : list.reversed()) {
            System.out.println(integer);
        }

    }
}



























