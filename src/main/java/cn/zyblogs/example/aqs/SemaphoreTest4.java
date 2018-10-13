package cn.zyblogs.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @Title: SemaphoreTest.java
 * @Package cn.zyblogs.example.aqs
 * @Description: TODO 信号量 同时允许的并发数
 * @Author ZhangYB
 * @Version V1.0
 */
@Slf4j
public class SemaphoreTest4 {

    private final static int THREAD_TOTAL = 20;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        // 允许的并发数
        final Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < THREAD_TOTAL ; i ++){
            final int threadNum = i ;
            executorService.execute(()->{
                try {
                    // 尝试获取一个许可
                    if (semaphore.tryAcquire(5000,TimeUnit.MILLISECONDS)){
                        test(threadNum);
                        // 释放
                        semaphore.release();
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        log.info("finish..." );
        executorService.shutdown();
    }

    private static void test(int threadNum) throws InterruptedException {
        log.info("{}",threadNum );
        Thread.sleep(1000);
    }
}
