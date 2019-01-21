package com.collector;

import com.stream.Dish;
import lambdasinaction.chap7.ForkJoinSumCalculator;
import lambdasinaction.chap7.ParallelStreams;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;

/**
 * 收集器demo
 */
public class CollectorTest {
    List<Dish> menuList = Arrays.asList(
            new Dish("pork", false, 800, Dish.Type.MEAT),
            new Dish("pork", false, 300, Dish.Type.MEAT),
            new Dish("beef", false, 700, Dish.Type.MEAT),
            new Dish("chicken", false, 400, Dish.Type.MEAT),
            new Dish("fish", false, 400, Dish.Type.FISH),
            new Dish("chicken", false, 500, Dish.Type.MEAT),
            new Dish("french", true, 200, Dish.Type.OTHERS),
            new Dish("rice", true, 300, Dish.Type.OTHERS)

    );

    /**
     * 利用collect(收集器)进行分组,归约汇总,字符串连接
     */
    @Test
    public void test1() {
        Map<Dish.Type, List<Dish>> collect = menuList.stream().collect(groupingBy(d -> d.getType()));
//        System.out.println(collect);
        Comparator<Dish> comparingInt = comparingInt(Dish::getCalories);
        Optional<Dish> max = menuList.stream().collect(maxBy(comparingInt));
        Integer sum = menuList.stream().collect(summingInt(Dish::getCalories));
        IntSummaryStatistics sumStatistics = menuList.stream().collect(summarizingInt(Dish::getCalories));
        // 连接字符串
        String joinNames = menuList.stream().map(Dish::getName).collect(joining(", "));
        // 广义归约汇总
        Integer bSum = menuList.stream().collect(reducing(0, Dish::getCalories, (i, j) -> i + j));
        Optional<Dish> maxR = menuList.stream()
                .collect(reducing((a1, a2) -> a1.getCalories() > a2.getCalories() ? a1 : a2));
//        System.out.println(bSum);
//        System.out.println(maxR);
//        System.out.println(sumStatistics);
//        System.out.println(max.get());
//        System.out.println(joinNames);
//        System.out.println(sum);

    }

    /**
     * 使用收集器进行多级分组
     */
    @Test
    public void test2() {
        // 先对menuList按Type进行一级分组,然后再按calories大小进行二级分组
        Map<Dish.Type, Map<CaloriesLevel, List<Dish>>> mutiGroupList = menuList
                .stream()
                .collect(groupingBy(d -> d.getType(), groupingBy(d -> {
                    if (d.getCalories() > 500) {
                        return CaloriesLevel.FAT;
                    }
                    if (d.getCalories() > 300 && d.getCalories() <= 500) {
                        return CaloriesLevel.NORMAL;
                    }
                    return CaloriesLevel.DIET;
                })));
//        System.out.println(mutiGroupList);
        // 分组,然后再对分组中数据进行归约并返回每一组的最大值
        Map<Dish.Type, Dish> groupMaxList = menuList
                .stream()
                .collect(groupingBy(d -> d.getType(),
                        collectingAndThen(maxBy(comparingInt(Dish::getCalories)), Optional::get)));
//        System.out.println(groupMaxList);

        // 查看各分组中都有哪些类型的calories
        Map<Dish.Type, Set<CaloriesLevel>> setMap = menuList.stream()
                .collect(groupingBy(Dish::getType, mapping(d -> {
                    if (d.getCalories() > 500) {
                        return CaloriesLevel.FAT;
                    }
                    if (d.getCalories() > 300 && d.getCalories() <= 500) {
                        return CaloriesLevel.NORMAL;
                    }
                    return CaloriesLevel.DIET;
                }, toCollection(HashSet::new))));
        System.out.println(setMap);
    }

    /**
     * 分区:本质是分组的一种特殊情况
     */
    @Test
    public void test3() {
        Map<Boolean, List<Dish>> booleanListMap = menuList.stream()
                .collect(partitioningBy(Dish::isVegetarian));
        Map<Boolean, Map<Dish.Type, List<Dish>>> multiBooleanMap = menuList.stream()
                .collect(partitioningBy(Dish::isVegetarian, groupingBy(Dish::getType)));
//        System.out.println(booleanListMap);
//        System.out.println(multiBooleanMap);
        List<Dish> dishList = menuList.stream().filter(d -> d.getCalories() > 500).collect(new ToListCollector<>());
//        System.out.println(dishList);
        // 并行累加
        long sum = Stream.iterate(1L, i -> i + 1).limit(10).parallel().mapToLong(Long::longValue).sum();
        System.out.println(sum);
    }

    /**
     * 将数字按质数和非质数分区
     */
    @Test
    public void test4() {
//        int sqrt = (int) Math.sqrt(8);
        Map<Boolean, List<Integer>> primeMap = IntStream
                .rangeClosed(2, 20).boxed()
                .collect(partitioningBy(this::isPrime));
//        System.out.println(primeMap);

        String dishJoinName = (String) menuList.stream()
                .sorted(comparing(com.stream.Dish::getCalories))
                .map(com.stream.Dish::getName)
                .collect(Collectors.joining(","));

        System.out.println(dishJoinName);
    }

    /**串并行流执行数值累加的时间对比
     */
    @Test
    public void test5(){
        long sequenceVal = measureSumPerf(this::sequentialSum, 1000_0000);
        System.out.println("sequenceVal:"+sequenceVal);
//        long parallelVal = measureSumPerf(this::parallelSum, 10000000);
//        System.out.println("parallelVal:"+parallelVal);
    }

    /**并行流的正确&错误使用
     */
    @Test
    public void test6(){
        //正确的串行使用
//        measureSumPerf(this::sideEffectSum,1000);
        measureSumPerf(this::sideEffectParallelSum,1000);
    }

    /**
     */
    @Test
    public void test7(){
        long sum = forkjoinSum(1000_0000);
        System.out.println(sum);
    }

    @Test
    public void test8(){
        char s=' ';
        System.out.println(Character.isWhitespace(s));
    }

    @Test
    public void test9(){

    }

    public long sideEffectSum(long n){
        ParallelStreams.Accumulator accumulator =new ParallelStreams.Accumulator();
        LongStream.rangeClosed(1L,n)
               .forEach(accumulator::add);
        return accumulator.total;
    }

    /**错误的并行累加
     * @param n
     * @return
     */
    public long sideEffectParallelSum(long n){
        ParallelStreams.Accumulator accumulator =new ParallelStreams.Accumulator();
        LongStream.rangeClosed(1L,n)
                .parallel()
                .forEach(accumulator::add);
        return accumulator.total;
    }

    /**计算累加器执行时间(执行10次,取最小)
     * @param adder
     * @param n
     * @return
     */
    public long measureSumPerf(Function<Long, Long> adder, long n) {
        long fastVal = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            Long sum = adder.apply(n);
            long oprTime = (System.nanoTime() - start);
            System.out.println("result:" + sum);
            if (oprTime < fastVal)
                fastVal = oprTime;
        }
        return fastVal;
    }

    /**使用forkjoin框架进行累加计算
     * @param n
     * @return
     */
    public long forkjoinSum(long n){
        long[] args = LongStream.rangeClosed(1L, n).toArray();
        long begin = System.nanoTime();
        ForkJoinTask<Long> forkJoinTask = new ForkJoinSumCalculator(args);
        Long sum = new ForkJoinPool().invoke(forkJoinTask);
        long end = System.nanoTime();
        System.out.println("forkjoinSum cost:"+(end-begin));
        return sum;
    }

    public boolean isPrime(int a) {
        return IntStream.range(2, a).noneMatch(i -> a % i == 0);
    }

    public long sequentialSum(long n){
        return LongStream.rangeClosed(1L,n)//使用此版本会快很多:直接生成原始类型数值
//                Stream.iterate(1L,i->i+1)
                .limit(n)
                .reduce(0L,Long::sum);
        
        
        
    }

    /**并行版累加
     * 比sequentialSum要慢很多,原因:
     * 1,iterate 生成的是装箱对象,必须拆箱才能求和
     * 2,拆分iterate 很难
     * @param n
     * @return
     */
    public long parallelSum(long n){
        return LongStream.rangeClosed(1L,n)//使用此版本会快很多:直接生成原始类型数值,更加易于拆分
//                Stream.iterate(1L,i->i+1)
                .limit(n)
                .parallel()
                .reduce(0L,Long::sum);
    }

}
