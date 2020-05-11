/*
 * @(#) Person
 * 版权声明 黄志军， 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2018
 * <br> Company:黄志军
 * <br> @author selfImpr
 * <br> 2018-07-30 19:12:42
 * <br> @description
 *
 *
 */

package java8Lamda.optionalTest.entity;

import java.util.Optional;

/**
 * 这边的场景就是说人，车，保险，保险公司
 * 人可能有车，可能没车
 * 有车，可能有保险，可能没保险，
 * 有保险就一定要有保险公司的名字
 */
public class Person {

    private Car car;

    private Optional<Car> car1;

    public Optional<Car> getCar1() {
        return car1;
    }

    public void setCar1(Optional<Car> car1) {
        this.car1 = car1;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
