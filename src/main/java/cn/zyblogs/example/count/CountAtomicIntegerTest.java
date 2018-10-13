package cn.zyblogs.example.count;

import cn.zyblogs.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Title: ConcurrentyTest.java
 * @Package cn.zyblogs.test
 * @Description: TODO
 * @Author ZhangYB
 * @Version V1.0
 */
@Slf4j
@ThreadSafe
public class CountAtomicIntegerTest {

    // 请求总数
    private static int clientTotal = 5000;

    /**
     *  同时并发执行的线程数
     */
    private static int threadTotal = 50;

    public static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        // 线程池
        ExecutorService executorService = Executors.newCachedThreadPool();

        // 信号量  同时允许并发的线程数
        final Semaphore semaphore = new Semaphore(threadTotal);

        // 定义计数器闭锁
        CountDownLatch countDownLatch = new CountDownLatch(clientTotal);

        for (int i = 0 ; i < clientTotal; i ++){
            executorService.execute(()->{
                try {
                    // 是否允许被执行 如果达到一定并发数 可能会临时阻塞
                    semaphore.acquire();
                    add();
                    // 释放
                    semaphore.release();
                } catch (InterruptedException e) {
                    log.error("exception", e);
                }
                // 每次减少1
                countDownLatch.countDown();
            });
        }
            //  countDownLatch.countDown();减到0 不再等待
            countDownLatch.await();
            // 关闭线程池
            executorService.shutdown();
            log.info("count:{}" ,count.get());
    }

    private static void add(){
        // 先增加 后后去值
        count.incrementAndGet();

        // 先获取值 在增加
//        count.getAndIncrement();

    }
}
