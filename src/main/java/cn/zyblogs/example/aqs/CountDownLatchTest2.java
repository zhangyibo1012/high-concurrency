package cn.zyblogs.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Title: CountDownLatchTest.java
 * @Package cn.zyblogs.example.aqs
 * @Description: TODO
 * @Author ZhangYB
 * @Version V1.0
 */
@Slf4j
public class CountDownLatchTest2 {

    private final static int THREAD_TOTAL = 200;

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();
        final CountDownLatch countDownLatch = new CountDownLatch(THREAD_TOTAL);
        for (int i = 0; i < THREAD_TOTAL; i++) {
            final int threadNum = i;
            executorService.execute(() -> {
                try {
                    test(threadNum);
                } catch (InterruptedException e) {
                    log.info("exception", e);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        //  countDownLatch.countDown();等待10毫秒 执行finish 之前给定的线程还会继续执行完
        countDownLatch.await(10, TimeUnit.MILLISECONDS);
        log.info("finish");
        // 关闭线程池
        executorService.shutdown();
    }

    private static void test(int threadNum) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(100);
        log.info("{}", threadNum);
    }
}
