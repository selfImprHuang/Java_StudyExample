/*
 * @(#) OptionalTest
 * 版权声明 黄志军， 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2018
 * <br> Company:黄志军
 * <br> @author selfImpr
 * <br> 2018-07-30 19:10:22
 * <br> @description
 *
 *
 */

package java8Lamda.optionalTest;

import java8Lamda.optionalTest.entity.Car;
import java8Lamda.optionalTest.entity.Insurance;
import java8Lamda.optionalTest.entity.Person;

import java.util.Optional;

public class OptionalTest {

    public void optionalTest() {
        /**
         * 根据java8实战的说法就是：由于Optional类设计时就没特别考虑将其作为类的字段使用，所以它也并未实现Serializable接口。
         * Optional的设计初衷仅仅是要支持能返回Optional对象的语法。
         *
         */

        Person p = new Person();
        Car car = new Car();
        Insurance i = new Insurance();

        /**
         * 这边的场景请看实体说明。
         */
        /**
         * 像p，我们去取得他的car，保险等等是没问题的。但是p1，我们去获取就会出现空指针的异常。所以我们需要很麻烦的判断
         * 主要针对NullPointException。所以我们使用Optional。
         */
        i.setInsuranceName("平安保险");
        car.setInsurance(i);
        p.setCar(car);

        /**
         * Optional的创建
         */
        //空对象
        Optional<Car> o1 = Optional.empty();
        //创建对象，空就报错
        Optional<Car> o2 = Optional.of(p.getCar());
        //可接受空的Optional
        Optional<Car> o3 = Optional.ofNullable(car);
        //通过new或者直接用参数创建一个Optional
        Optional<Person> p1 = Optional.of(p);


        /**
         * 使用map遍历Optional
         */
        //这个地方不会报错的，因为我们的person里面没有用optional包了一层car...
        Optional<String> name = p1.map(Person::getCar).map(Car::getInsurance).map(Insurance::getInsuranceName);

        //我们使用flatMap遍历被Optional包裹的属性
        String name1 = p1.flatMap(Person::getCar1).flatMap(Car::getInsurance1).map(Insurance::getInsuranceName).orElse("123");
        //判断Optional是不是有值
        p1.flatMap(Person::getCar1).isPresent();

        /**
         * Optional有一类类似过滤、函数流的操作，
         * 这里不做赘述
         */


    }
}
