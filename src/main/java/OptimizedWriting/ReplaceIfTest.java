package OptimizedWriting;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * 通过不同的写法替代if else的使用
 */
public class ReplaceIfTest {


    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException {

        ifWritingMethod(60);
        ternaryOperator(60);
        switchCase("A");
        methodEnum(Score.A);
        tableDriven(60,2,"A");

         //通过map的方式进行方法的反射处理:有点类似表驱动法
         //参考地址：https://blog.csdn.net/qq_36865969/article/details/80155776
        HashMap<String, Method> methodMap = new HashMap<>();
        methodMap.put("A", ReplaceIfTest.class.getMethod("A"));
        methodMap.put("B", ReplaceIfTest.class.getMethod("B"));
        methodMap.put("C", ReplaceIfTest.class.getMethod("C"));
        ReplaceIfUtil replaceIfUtil = new ReplaceIfUtil(methodMap,ReplaceIfTest.class);
        mapInvoke("A",replaceIfUtil);
    }

    /**
     * 原始的if else写法
     */
    public static void ifWritingMethod(int score) {
        if (score < 60) {
            System.out.println("不及格");
        } else if (score < 80) {
            System.out.println("中等");
        } else {
            System.out.println("优秀");
        }
    }

    /**
     * 三目运算符写法
     */
    public static void ternaryOperator(int score) {
        String message = score < 60 ? "不及格" : score < 80 ? "中等" : "优秀";
        System.out.println(message);
    }

    /**
     * switch case的写法
     */
    public static void switchCase(String param) {
        switch (param) {
            case "A":
                System.out.println("优秀");
                break;
            case "B":
                System.out.println("中等");
                break;
            case "C":
                System.out.println("不及格");
                break;
            default:
                System.out.println("未知");
        }
    }


    /**
     * 直接通过映射枚举的写法
     */
    public static void methodEnum(Score score) {
        score.coreOut();
    }

    private enum Score {
        /**
         * A等级
         */
        A {
            @Override
            public void coreOut() {
                System.out.println("优秀");
            }
        },
        /**
         * B等级
         */
        B {
            @Override
            public void coreOut() {
                System.out.println("中等");
            }
        },
        /**
         * C等级
         */
        C {
            @Override
            public void coreOut() {
                System.out.println("不及格");
            }
        };

        public abstract void coreOut();
    }




    /**
     * 通过策略者模式
     */
    public void Strategist(String score) {

    }



    /**
     * 表驱动法
     * 参考地址：https://baike.baidu.com/item/%E8%A1%A8%E9%A9%B1%E5%8A%A8%E6%B3%95/15768054?fr=aladdin
     */
    private static int[] level = {80, 60, 0};
    private static String[] out = {"优秀", "中等", "不及格"};
    private static int day[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static HashMap map = new HashMap() {
        {
            put("A", "优秀");
            put("B", "中等");
            put("C", "不及格");
        }
    };
    public static void tableDriven(int score, int month, String s) {
        //对应成绩输出信息
        for (int i = 0; i < level.length; i++) {
            if (score > level[i]) {
                System.out.println(out[i]);
                break;
            }
        }

        //输出每个月有几天
        System.out.println(day[month - 1]);

        //通过map找到对应的输出内容
        System.out.println(map.get(s));
    }

    public static void mapInvoke(String param, ReplaceIfUtil replaceIfUtil) {
        replaceIfUtil.doIf("A");
    }
    public void A() {
        System.out.println("优秀");
    }

    public void B() {
        System.out.println("中等");
    }

    public void C() {
        System.out.println("不及格");
    }
}
