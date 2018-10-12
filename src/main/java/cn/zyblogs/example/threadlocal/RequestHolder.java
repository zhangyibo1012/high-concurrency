package cn.zyblogs.example.threadlocal;

/**
 * @Title: RequestHodler.java
 * @Package cn.zyblogs.example.threadlocal
 * @Description: TODO
 * @Author ZhangYB
 * @Version V1.0
 */
public class RequestHolder {

    private final static ThreadLocal<Long> requestHolder = new ThreadLocal<>();

    public static void add(long id) {
        requestHolder.set(id);
    }

    public static Long getId(){
        return requestHolder.get();
    }

    public static void remove(){
        requestHolder.remove();
    }


}
