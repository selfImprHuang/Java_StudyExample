package OptimizedWriting;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class ReplaceIfUtil {

    /**
     * 某某值对应执行某个方法的映射map
     */
    private HashMap<String, Method> methodMap;

    /**
     * 被映射执行方法的类
     */
    private Class aclass;

    public ReplaceIfUtil(HashMap<String, Method> methodMap, Class<ReplaceIfTest> aclass) {
        this.aclass = aclass;
        this.methodMap= methodMap;
    }

    public ReplaceIfUtil(HashMap<String, Method> methodMap) {
        this.methodMap = methodMap;
    }

   public  void doIf(String key,Object... param){
       Method method =  this.methodMap.get(key);
       if (null == method ){
           throw new RuntimeException("找不到对应的执行方法");
       }
       try {
           method.invoke(this.aclass.newInstance(),param);
       } catch (IllegalAccessException |InvocationTargetException |InstantiationException e) {
         throw new RuntimeException("执行对应方法报错",e);
       }

   }
}
