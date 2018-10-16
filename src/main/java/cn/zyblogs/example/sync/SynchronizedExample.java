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
public class SynchronizedExample {

    public static void main(String[] args) {
        SynchronizedExample example = new SynchronizedExample();
        SynchronizedExample example2 = new SynchronizedExample();
        //使用线程池方法进行测试：
        ExecutorService executorService = Executors.newCachedThreadPool();

//        executorService.execute(()-> example.test1());
//        executorService.execute(()-> example.test1());

        executorService.execute(() -> example.test1());
        executorService.execute(() -> example2.test1());

        executorService.shutdown();


    }

//    /**
//     *  修饰方法
//     */
//    public synchronized void test2(){
//       IntStream.range(0, 10).forEach((i)-> log.info("test2 - {}",i ));
//    }

    public void test1() {
        // 修饰代码块 被修饰的代码称为同步语句块，作用的范围是大括号括起来的部分。作用的对象是调用这段代码的对象。
        synchronized (this) {
            IntStream.range(0, 10).forEach((i) -> log.info("test1 - {}", i));
        }
    }
}
