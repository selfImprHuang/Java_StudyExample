/*
 * @(#) BeanUtilsTestController
 * 版权声明 黄志军， 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2018
 * <br> Company:黄志军
 * <br> @author selfImpr
 * <br> 2018-04-17 22:14:42
 * <br> @description
 *
 *
 */

package beanUtils;

import beanUtils.bean.BeanUtilsTestBean;
import beanUtils.bean.BeanUtilsTestBean1;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanUtilsTestController {


    public static void main(String[] args)  throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        BeanUtilsTestBean b1 = new BeanUtilsTestBean();
        BeanUtilsTestBean1 b2 = new BeanUtilsTestBean1();
        b1.setTestInt(1);
        b1.setTestString("abc");
        Map map = new HashMap();
        map.put("1", "a");
        map.put("2", "b");
        b1.setTestMap(map);
        List list = new ArrayList();
        list.add("list1");
        list.add("list2");
        b1.setTestList(list);


        BeanUtilsTestBean b3 = (BeanUtilsTestBean) BeanUtils.cloneBean(b1);
        System.out.println((b1.equals(b3)) ? "浅克隆" : "深克隆");

        BeanUtils.copyProperties(b2, b1);
        System.out.println(b2.getTestString().equals(b1.getTestString()) ? "克隆成功" : "克隆失败");


        Map maps = BeanUtils.describe(b1);
        System.out.println(maps.toString());

        String[] s1 = BeanUtils.getArrayProperty(b1, "testList");
        System.out.println(s1.toString());

        String[] s2 = BeanUtils.getArrayProperty(b1, "testMap");
        System.out.println(s2.toString());

        String result = BeanUtils.getSimpleProperty(b1, "testInt");
        System.out.println(result);

        BeanUtilsTestBean b5 = new BeanUtilsTestBean();
        maps.remove("testList");
        maps.remove("testMap");
        BeanUtils.populate(b5, maps);
        System.out.print(b5.getTestString().equals(b1.getTestString()) ? "转换map成功" : "转换map失败");


        BeanUtils.setProperty(b5, "testList", b1.getTestList());
        System.out.println(b5.getTestList().toString());
        String result2 = BeanUtils.getSimpleProperty(b1, "testMap");
        System.out.println(result2);


    }
}
