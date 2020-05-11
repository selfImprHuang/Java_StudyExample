/*
 * @(#) StreamTest
 * 版权声明 黄志军， 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2018
 * <br> Company:黄志军
 * <br> @author selfImpr
 * <br> 2018-07-26 09:52:58
 * <br> @description
 *
 *
 */

package java8Lamda.streamTest;

import java8Lamda.streamTest.entity.ListEntity;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamTest {

    public static void main(String[] args) throws IOException {
        List<ListEntity> list = makeList();
        /**
         * 筛选和切片
         */
        //筛选操作
        list.stream().filter(listEntity -> listEntity.getAge() > 10).collect(Collectors.toList());
        //返回去重操作的数据
        list.stream().distinct().collect(Collectors.toList());
        //截断流，只取前面多少个数据
        list.stream().limit(3).collect(Collectors.toList());
        //跳过某个元素
        list.stream().skip(2).collect(Collectors.toList());

        /**
         * 映射操作
         */
        //获取流中的某个元素
        List<String> s = list.stream().map(ListEntity::getName).collect(Collectors.toList());
        List<Integer> i = list.stream().map(ListEntity::getAge).collect(Collectors.toList());
        //流的扁平化。（一言以蔽之，flatmap方法让你把一个流中的每个值都换成另一个流，然后把所有的流连接起来成为一个流）
        //这边其实是为了把s里面的数据分成单个字母，使用下面的方法，操作的结果不会去重，上面的扁平化会去重，可参考《java8实战》
        s.stream().map(word -> word.split("")).flatMap(Arrays::stream).distinct().collect(Collectors.toList());
        s.stream().map(word -> word.split("")).distinct().collect(Collectors.toList());

        /**
         * 查找和匹配
         * anyMatch、allMatch和noneMatch这三个操作都用到了我们所谓的短路，这就是大家熟悉的Java中&&和||运算符短路在流中的版本。
         */
        //是否存在（返回布尔值）
        boolean b = s.stream().anyMatch(word -> "xiaoming".equals(word));
        //是否所有东西都匹配这个条件（返回布尔值）
        b = s.stream().allMatch(word -> word.length() == 2);
        //没有匹配
        b = s.stream().noneMatch(word -> word.length() > 100);

        //查找元素(Optional能够帮助处理null。这边可以返回大部分实体)
        //Optional<T>类（java.util.Optional）是一个容器类，代表一个值存在或不存在。(方法介绍如下)：
        /**
         * isPresent()将在Optional包含值的时候返回true, 否则返回false。
         * ifPresent(Consumer<T> block)会在值存在的时候执行给定的代码块。
         * T get()会在值存在时返回值，否则抛出一个NoSuchElement异常。
         * T orElse(T other)会在值存在时返回值，否则返回一个默认值。
         */
        Optional<String> optional = s.stream().filter(word -> word.equals("xiaoming1")).findAny();
        System.out.printf(optional.equals(Optional.empty()) ? "空" : "不为空");
        Optional<ListEntity> optionalListEntity = list.stream().filter(listEntity -> listEntity.getName().equals("1111")).findFirst();
        System.out.printf(optional.equals(Optional.empty()) ? "空" : optionalListEntity.get().getName());


        /**
         * 规约操作--数字的操作提供了指定的stream可参考，如果比较复杂的操作
         */
        //计算总数，计算最大值，计算最小值,流的元素数量 --可以直接用Integer有的方法替代，不用自己去写实现
        i.stream().reduce(0, (x, y) -> x + y);
        i.stream().reduce(Integer::sum);
        i.stream().reduce(Integer::max);
        i.stream().reduce(Integer::min);
        i.stream().count();
        //避免装箱操作而引入的IntStream、DoubleStream和LongStream
        i.stream().mapToInt(a -> a).sum();

        /**
         * 创造流
         */
        //由值创建
        Stream<String> stream = Stream.of("a", "b", "cd");
        //由数组创建
        IntStream intStream = Arrays.stream(new int[]{1, 2, 3});
        //由文件创建（lines，它会返回一个由指定文件中的各行构成的字符串流。）
        Stream<String> stream1 = Files.lines(Paths.get("data.txt"), Charset.defaultCharset());
        //由函数生成


        /**
         * 用流收集数据
         */
        //计算最大值（略）
        //汇总（略）
        //连接字符串
        String joinS = s.stream().collect(Collectors.joining(","));

        //分组 --这边除了可以返回属性，还可以返回统计信息,还可以返回set或者list，直接组成一个数据结构
        Map<Integer, List<ListEntity>> map = list.stream().collect(Collectors.groupingBy(ListEntity::getAge));
        Map<Integer, Long> map1 = list.stream().collect(Collectors.groupingBy(ListEntity::getAge, Collectors.counting()));
        Map<Integer, Set<String>> map2 = list.stream().collect(Collectors.groupingBy(ListEntity::getAge, Collectors.mapping
            (ListEntity::getName, Collectors.toSet())));
        //多级分组。这边return的值是作为map的key(这边展示这么多的group的写法，就是像说明，stream的这个方法可以处理大部分数据结构)
        Map<Integer, Map<String, List<ListEntity>>> map3 = list.stream().collect(Collectors.groupingBy(ListEntity::getAge, Collectors
            .groupingBy(listEntity -> {
                if (listEntity.getMoney() < 2000) {
                    return "没钱人";
                } else {
                    return "有钱人";
                }
            })));

        map3.forEach((a, m) -> {
            System.out.printf(a + "岁的人是【");
            m.forEach((ss, lists) -> {
                System.out.printf(ss + ":");
                lists.forEach(listEntity -> System.out.printf("名字是：" + listEntity.getName() + "】"));
            });
        });
        //我记得在畅享的一次代码走查说过一个市区县的树结构的构建，其实可以使用多级分组。（他的区分条件是什么的长度）出来的一个数据结构：
        /*
        [{
            "莆田": [{
                "涵江": ["梧塘", "枫林"]
            }, {}]
        }, {
            "北京": [{
                "朝阳": ["A", "B"]
            }, {}]
        }]
        */
        //分区：分区和分组差不多，就是他的返回结果为boolean，所以最多有true和false两个，也是支持多级分组的（此处不做赘述）
        Map<Boolean, List<ListEntity>> mapb = list.stream().collect(Collectors.partitioningBy(listEntity -> listEntity.getAge() > 10));
        //返回类型的转换(略)


    }

    public static List<ListEntity> makeList() {
        List<ListEntity> list = new ArrayList<>();
        ListEntity l1 = new ListEntity();
        l1.setName("xiaoming");
        l1.setAge(10);
        l1.setMoney(1000);
        l1.setBirthday(new Date());

        ListEntity l2 = new ListEntity();
        l2.setName("xiaohong");
        l2.setAge(20);
        l2.setMoney(2000);
        l2.setBirthday(new Date());

        ListEntity l3 = new ListEntity();
        l3.setName("xiaoliang");
        l3.setAge(30);
        l3.setMoney(3000);
        l3.setBirthday(new Date());

        ListEntity l4 = new ListEntity();
        l4.setName("xiaozhu");
        l4.setAge(40);
        l4.setMoney(4000);
        l4.setBirthday(new Date());


        list.add(l1);
        list.add(l2);
        list.add(l3);
        list.add(l4);
        return list;
    }
}
