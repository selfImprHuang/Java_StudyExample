/*
 * @(#) Car
 * 版权声明 黄志军， 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2018
 * <br> Company:黄志军
 * <br> @author selfImpr
 * <br> 2018-07-30 19:13:33
 * <br> @description
 *
 *
 */

package java8Lamda.optionalTest.entity;

import java.util.Optional;

public class Car {

    private Insurance insurance;

    private Optional<Insurance> insurance1;

    public Optional<Insurance> getInsurance1() {
        return insurance1;
    }

    public void setInsurance1(Optional<Insurance> insurance1) {
        this.insurance1 = insurance1;
    }

    public Insurance getInsurance() {
        return insurance;
    }

    public void setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }
}
