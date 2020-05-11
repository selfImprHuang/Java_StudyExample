/*
 * @(#) ConsumerTest
 * 版权声明 黄志军， 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2018
 * <br> Company:黄志军
 * <br> @author selfImpr
 * <br> 2018-07-25 22:35:34
 * <br> @description
 *
 *
 */

package java8Lamda.functionTest;


import java8Lamda.streamTest.entity.ListEntity;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class FunctionTest {

    /**
     * 这边我们主要讲的是 Consumer、Function和Predicate
     */
    public static void main(String[] args) {
        /**
         * Predicate的用法
         * 这个东西可以作为对一个传入的函数参数进行判断的一个操作
         *
         * Predicate<T> 接受一个输入参数，返回一个布尔值结果。该接口包含多种默认方法来将Predicate组合成其他复杂的逻辑（比如：与，或，非）。
         * 可以用于接口请求参数校验、判断新老数据是否有变化需要进行更新操作。add--与、or--或、negate--非
         */
        Predicate p;
        predicate(Arrays.asList(1, 2, 3, 4, 5, 6), integer -> integer > 2, integer -> integer < 7);

        /**
         * Consumer的用法
         * java.util.function.Consumer<T>定义了一个名叫accept的抽象方法，它接受泛型T的对象，没有返回（void）。
         * 网上很多都说Consumer主要是处理类似数据库更新的一些返回void的操作，这边的话根绝个人接触到的，人为他还有一个就是环绕效果，去除重复代码
         * 比方说我们的获取列表的操作，尤其是我们用mybatis做分页的话，其实是执行那个语句就行了。所以很多差别就是一个代码的区别
         */
        Consumer c;
        consumer(s -> System.out.printf(s), s -> System.out.printf("after accept " + s), "做一个测试");

        /**
         * Function的用法
         * java.util.function.Function<T, R>接口定义了一个叫作apply的方法，它接受一个泛型T的对象，并返回一个泛型R的对象。如
         */
        Function f;
        function(10, a -> a + 10);

        /**
         * Supplier的用法
         * 网上的意思是说这个类，他可以被先声明出来，等你要用它的时候才会去创建。这个就很神奇了啊
         *
         * 在开发中，我们经常会遇到一些需要延迟计算的情形，比如某些运算非常消耗资源，如果提前算出来却没有用到，会得不偿失。
         * 在计算机科学中，有个专门的术语形容它：惰性求值。
         * 惰性求值是一种求值策略，也就是把求值延迟到真正需要的时候。
         * 在Java里，我们有一个专门的设计模式几乎就是为了处理这种情形而生的：Proxy。不过，现在我们有了新的选择：Supplier。
         */
        Supplier<ListEntity> s = ListEntity::new;
        s.get();//这个时候才被创建出来
        Supplier<String> s1 = () -> complete();
        System.out.printf("这个复杂的操作现在才做"+s1.get());
    }

    public static List<Integer> predicate(List<Integer> list, Predicate<Integer> p, Predicate<Integer> p1) {
        //只使用一个条件
        list.stream().filter(p).collect(Collectors.toList());
        //使用两个条件的迭代
        list.stream().filter(p.and(p1)).collect(Collectors.toList());//且
        list.stream().filter(p.or(p1)).collect(Collectors.toList());//或
        list.stream().filter(p.negate()).collect(Collectors.toList());//非
        return list;
    }

    public static void consumer(Consumer<String> fn, Consumer<String> fn1, String s) {
        fn.accept(s);

        //先执行fn，再执行fn1
        fn.andThen(fn1).accept(s);
    }

    public static int function(int a, Function<Integer, Integer> f) {
        //function操作
        f.apply(a);
        //两个参数的迭代操作
        return f.andThen(f).apply(a);
    }


    public static String complete() {
        return "这是一个非常复杂的操作";
    }
}
