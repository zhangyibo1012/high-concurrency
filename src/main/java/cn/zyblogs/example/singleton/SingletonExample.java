package cn.zyblogs.example.singleton;

/**
 * @Title: SingLetonExample.java
 * @Package cn.zyblogs.example.singleton
 * @Description: TODO
 * @Author ZhangYB
 * @Version V1.0
 */
public class SingletonExample {

    private SingletonExample(){
    }

    private static class InstanceHolder{
        private final static SingletonExample instance = new SingletonExample();
    }

    public static SingletonExample getInstance(){
        return InstanceHolder.instance;
    }
}
