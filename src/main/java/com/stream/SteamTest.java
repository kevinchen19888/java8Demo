package com.stream;

import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class SteamTest {
    List<Dish> menuList = Arrays.asList(
            new Dish("pork", false, 800, Dish.Type.MEAT),
            new Dish("beef", false, 700, Dish.Type.MEAT),
            new Dish("chicken", false, 400, Dish.Type.MEAT),
            new Dish("french", true, 200, Dish.Type.OTHERS),
            new Dish("rice", true, 300, Dish.Type.OTHERS)
    );

    @Test
    public void test1() {
        List<Dish> menuList = Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beef", false, 700, Dish.Type.MEAT),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french", true, 200, Dish.Type.OTHERS),
                new Dish("rice", true, 300, Dish.Type.OTHERS)
        );
        // 对菜单集合进行流操作:1,筛选calories<500的,2.然后进行排序,
        // 3将菜单转换为对应的菜单名,4.取前3项
        List<String> dishList = menuList.stream()
                .filter(d -> {
//                    System.out.println("filter:" + d.getName());
                    return d.getCalories() < 500;
                })
                .sorted(Comparator.comparing(Dish::getCalories))
                .map(d -> {
//                    System.out.println("map:" + d.getName());
                    return d.getName();
                })
                .limit(3)
                .skip(1)// 跳过前n个元素
                .collect(toList());
//        System.out.println(dishList);
        // 映射
        List<Integer> mapList = menuList.stream()
                .map(Dish::getName)
                .map(String::length)// 依赖最后一个map
                .collect(toList());
//        System.out.println(mapList);

        // 找出所有MEAT类型Dish,并求卡路里之和;TODO
        Integer sumCalories = menuList.stream()
                .filter(a -> a.getType() == Dish.Type.MEAT)
                .map(a -> a.getCalories())
                .reduce(0, Integer::sum);
        System.out.println(sumCalories);
    }

    /**
     * 流的扁平化
     */
    @Test
    public void test3() {
        List<String> words = Arrays.asList("hello", "world");
        List<String> wordList = words.stream()
                .map(a -> a.split(""))
                .flatMap(Arrays::stream)// 将各元素生成的流合并
                .distinct()
                .collect(toList());
//        System.out.println(wordList);

        // 给定两个数字列表,返回一个数对列表
        List<Integer> list1 = Arrays.asList(1, 3);
        List<Integer> list2 = Arrays.asList(2, 4);
        List<int[]> numPairList = list1.stream()
                .flatMap(i -> list2.stream().map(j -> new int[]{i, j}))// TODO
                .collect(toList());

        for (int[] i : numPairList) {
            for (int j = 0; j < i.length; j++) {
                System.out.print(i[j] + " ");
            }
            System.out.println();
        }
    }

    /**
     *
     */
    @Test
    public void test2() {
        List<Integer> list = Arrays.asList(1, 2, 3, 2, 5, 8);
        // 通过stream打印集合中不重复的偶数
      /*  list.stream()
                .filter(d -> d % 2 == 0)
                .distinct()
                .forEach(System.out::println);*/
        // 匹配
        boolean anyMatch = list.stream().anyMatch(i -> i == 2);
        boolean allMatch = list.stream().allMatch(i -> i > 10);
        boolean noneMatch = list.stream().noneMatch(i -> i < 0);
//        System.out.println(anyMatch);
//        System.out.println(allMatch);
//        System.out.println(noneMatch);
        // 查找
        Optional<Integer> first = list.stream().filter(i -> i < 5).findFirst();
        // 不考虑返回顺序的话,尽量使用findAny
        Optional<Integer> any = list.stream().filter(i -> i < 5).findAny();
//        System.out.println(first.get());
//        System.out.println(any.get());
        // 归约操作
        Integer reduce = list.stream().reduce(0, /*(a, b) -> a + b*/Integer::sum);
        Optional<Integer> max = list.stream().reduce(Integer::max);
        System.out.println(reduce);
        int[] a = new int[10];
        IntStream intStream = Arrays.stream(a);
    }

    /**
     * 原始类型特化流
     */
    @Test
    public void test4() {
        int sum = menuList.stream().mapToInt(d -> d.getCalories()).sum();
        OptionalDouble average = menuList.stream().mapToInt(d -> d.getCalories()).average();
        OptionalInt min = menuList.stream().mapToInt(d -> d.getCalories()).min();
//        System.out.println(sum);
//        System.out.println(average.orElse(0));
//        System.out.println(min.orElse(0));
        long count = IntStream.rangeClosed(1, 100).filter(i -> i % 2 == 0).count();//数值范围流
        System.out.println(count);


    }

    /**
     * 由值创建流
     */
    @Test
    public void test5() {
        Integer reduce = Stream.of("kevin", "james", "jane")
                .map(s -> s.length())
                .reduce(0, (a, b) -> a + b);
        System.out.println(reduce);
    }

    /**
     * 由数组创建流
     */
    @Test
    public void test6() {
        int[] arr = new int[]{1, 3, 8, 9};
        System.out.println(Arrays.stream(arr).filter(a -> a > 6).count());
    }

    /**
     * 由文件创建流
     */
    @Test
    public void test7() {
        try (Stream<String> lines = Files.lines(Paths.get(("F:\\MyFiles\\MyWorkSpace\\java8-demo" +
                "\\src\\main\\resources\\stream_demo.txt")), Charset.defaultCharset())) {
            long count = lines
                    .flatMap(line -> Arrays.stream(line.split(" ")))
                    .distinct()
                    .count();
            System.out.println(count);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建无线流
     */
    @Test
    public void test8() {
        Stream.iterate(0, n -> n + 2).limit(6).forEach(System.out::println);
    }

    /**
     * 斐波那契元组序列
     */
    @Test
    public void test9() {
        Stream.iterate(new int[]{0, 1}, a -> new int[]{a[1], a[0] + a[1]})
                .limit(1)
                .map(a -> a[0] + " " + a[1])
                .forEach(System.out::println);
        Stream.generate(Math::random).limit(5).forEach(System.out::println);
    }

}
