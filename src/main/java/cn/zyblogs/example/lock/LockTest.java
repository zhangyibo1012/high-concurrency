package cn.zyblogs.example.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Title: LockTest.java
 * @Package cn.zyblogs.example.lock
 * @Description: TODO
 * @Author ZhangYB
 * @Version V1.0
 */
@Slf4j
public class LockTest {

    // 请求总数
    private static final int CLIENT_TOTAL = 5000;
    private static final Lock lock = new ReentrantLock();
    /**
     * 同时并发允许的执行数
     */
    private static int threadTotal = 200;
    private static int count = 0;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(CLIENT_TOTAL);
        for (int i = 0; i < CLIENT_TOTAL; i++) {
            executor.execute(() -> {

                try {
                    // 获取一个许可
                    semaphore.acquire();
                    add();
                    // 释放
                    semaphore.release();
                } catch (InterruptedException e) {
                    log.info("exception", e);
                } finally {
                    // 执行一次 减少1
                    countDownLatch.countDown();
                }
            });
        }
        // countDownLatch.countDown();较少到0 停止等待 继续往下执行
        countDownLatch.await();
        executor.shutdown();
        log.info("count:{}", count);
    }

    private static void add() {
        try {
            lock.lock();
            count++;
        } finally {
            lock.unlock();
        }
    }
}
