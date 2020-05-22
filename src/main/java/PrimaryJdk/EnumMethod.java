package PrimaryJdk;


/**
 * 这个类用来测试通过枚举区分不同方法的使用
 * 这个操作可以在前端传入枚举映射的时候，通过转换到枚举来调用该枚举对应的实现方法
 * 方法的出入参应该一致，否则没办法达到效果
 */
public class EnumMethod {

    public static void main(String[] args) {
        for (MyEnum aEnum : MyEnum.values()){
            aEnum.saySomething();
            aEnum.doSomething();
        }
    }
}
