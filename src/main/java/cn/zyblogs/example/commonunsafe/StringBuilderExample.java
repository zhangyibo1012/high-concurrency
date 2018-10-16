package cn.zyblogs.example.commonunsafe;

import cn.zyblogs.annoations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @Title: StringBuilderExample.java
 * @Package cn.zyblogs.example.commonunsafe
 * @Description: TODO 不安全
 * @Author ZhangYB
 * @Version V1.0
 */
@Slf4j
@NotThreadSafe
public class StringBuilderExample {

    public static StringBuilder stringBuilder = new StringBuilder();
    // 请求总数
    private static int clientTotal = 5000;
    /**
     * 同时并发执行的线程数
     */
    private static int threadTotal = 50;

    public static void main(String[] args) {
        // 线程池
        ExecutorService executorService = Executors.newCachedThreadPool();

        // 信号量  同时允许并发的线程数
        final Semaphore semaphore = new Semaphore(threadTotal);

        // 所有的请求次数结束统计结果
        CountDownLatch countDownLatch = new CountDownLatch(clientTotal);

        for (int i = 0; i < clientTotal; i++) {
            executorService.execute(() -> {
                try {
                    // 是否允许被执行 如果达到一定并发数 可能会临时阻塞
                    semaphore.acquire();
                    update();
                    // 释放
                    semaphore.release();
                } catch (InterruptedException e) {
                    log.error("exception", e);
                }
                countDownLatch.countDown();
            });
        }
        try {
            //  countDownLatch.countDown();减到0 不再等待
            countDownLatch.await();
            // 关闭线程池
            executorService.shutdown();
            log.info("length:{}", stringBuilder.length());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void update() {
        stringBuilder.append("1");

    }
}
