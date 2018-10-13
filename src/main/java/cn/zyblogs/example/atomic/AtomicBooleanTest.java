package cn.zyblogs.example.atomic;

import cn.zyblogs.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Title: AtomicBooleanTest.java
 * @Package cn.zyblogs.example.atomic
 * @Description: TODO
 * @Author ZhangYB
 * @Version V1.0
 */
@Slf4j
@ThreadSafe
public class AtomicBooleanTest {


    // 请求总数
    private static int clientTotal = 5000;

    /**
     *  同时并发执行的线程数
     */
    private static int threadTotal = 50;



    /**
     *   原子性操作 从false变成true 只会执行一次 剩下4999次都是true
     *   实际中让某一段代码只执行一次 可以参考
     */
    private static AtomicBoolean isHappend = new AtomicBoolean();

    public static void main(String[] args) throws InterruptedException {

        // 线程池
        ExecutorService executorService = Executors.newCachedThreadPool();

        //  信号量  同时允许并发的线程数
        final Semaphore semaphore = new Semaphore(threadTotal);

        // 定义计数器闭锁
        CountDownLatch countDownLatch = new CountDownLatch(clientTotal);

        for (int i = 0; i < clientTotal ; i ++){
            executorService.execute(()->{

                try {
                    // 是否允许被执行 如果达到一定的线程并发数 可能会发生阻塞
                    semaphore.acquire();
                    test();
                    // 释放
                    semaphore.release();
                } catch (InterruptedException e) {
                    log.error("exception", e);
                }
                // 每执行一次 就减少1
                countDownLatch.countDown();
            });
        }

        // 减少到0 就停止await
        countDownLatch.await();
        // 关闭线程池
        executorService.shutdown();
        log.info("isHappend:{}", isHappend.get());
    }
    private static void test() {
        // 如果当前值是false 把它变成true
        if (isHappend.compareAndSet(false, true)){
            log.info("execute");
        }
    }
}
