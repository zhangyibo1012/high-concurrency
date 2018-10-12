package cn.zyblogs.example.sync;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * @Title: SynchronizedExample.java
 * @Package cn.zyblogs.example.sync
 * @Description: TODO
 * @Author ZhangYB
 * @Version V1.0
 */
@Slf4j
public class SynchronizedExample2 {

    /**
     *   修饰一个类
     */
   public static void test1(){

        synchronized (SynchronizedExample2.class){
            IntStream.range(0, 10).forEach((i)-> log.info("test1 - {}",i ));
        }
    }

    /**
     *  修饰一个静态方法
     */
    public static synchronized void test2(){
       IntStream.range(0, 10).forEach((i)-> log.info("test2 - {}",i ));
    }

    public static void main(String[] args) {
        SynchronizedExample2 example = new SynchronizedExample2();
        SynchronizedExample2 example2 = new SynchronizedExample2();
        ExecutorService executorService = Executors.newCachedThreadPool();

//        executorService.execute(()-> example.test1());
//        executorService.execute(()-> example.test1());

        executorService.execute(()-> test1());
        executorService.execute(()-> test2());

        executorService.shutdown();


    }
}
