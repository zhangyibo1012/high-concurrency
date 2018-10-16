package cn.zyblogs.example.singleton;

import java.util.stream.IntStream;

/**
 * @Title: SingletonEnum.java
 * @Package com.zyblogs.concurrency.pattern
 * @Description:
 * @Author ZhangYB
 * @Version V1.0
 */
public class SingletonEnum {

    private SingletonEnum() {
    }

    public static SingletonEnum getInstance() {
        return Singleton.INSTANCE.getInstance();
    }

    public static void main(String[] args) {


        IntStream.rangeClosed(1, 100).forEach(i -> new Thread(String.valueOf(i)) {
            @Override
            public void run() {
                System.out.println(SingletonEnum.getInstance());
            }
        }.start());
    }

    /**
     * 枚举类型 构造函数私有,只会被装载一次。 线程安全的
     */
    private enum Singleton {
        INSTANCE;

        private final SingletonEnum instance;

        Singleton() {
            instance = new SingletonEnum();
        }

        public SingletonEnum getInstance() {
            return instance;
        }

    }


}
