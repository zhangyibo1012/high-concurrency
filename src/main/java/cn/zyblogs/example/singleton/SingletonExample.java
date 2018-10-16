package cn.zyblogs.example.singleton;

/**
 * @Title: SingLetonExample.java
 * @Package cn.zyblogs.example.singleton
 * @Description: TODO
 * @Author ZhangYB
 * @Version V1.0
 */
public class SingletonExample {

    private SingletonExample() {

    }

    public static SingletonExample getInstance() {
        return InstanceHolder.instance;
    }

    private static class InstanceHolder {
        private static final SingletonExample instance = new SingletonExample();
    }


//    private SingletonExample(){
//    }
//
//    private static class InstanceHolder{
//        private final static SingletonExample instance = new SingletonExample();
//    }
//
//    public static SingletonExample getInstance(){
//        return InstanceHolder.instance;
//    }
}
