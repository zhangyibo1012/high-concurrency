package cn.zyblogs.example.threadlocal;

/**
 * @Title: RequestHodler.java
 * @Package cn.zyblogs.example.threadlocal
 * @Description: TODO
 * @Author ZhangYB
 * @Version V1.0
 */
public class RequestHolder {
    /**
     * ThreadLocal线程封闭 ThreadLocal内部维护了一个map,map的key是每个线程的名称，而map的value就是我们要封闭的对象。ThreadLocal提供了get、set、remove方法，每个操作都是基于当前线程的，所以它是线程安全的。
     */
    private final static ThreadLocal<Long> requestHolder = new ThreadLocal<>();

    public static void add(long id) {
        requestHolder.set(id);
    }

    public static Long getId() {
        return requestHolder.get();
    }

    public static void remove() {
        requestHolder.remove();
    }


}
