package cn.zyblogs.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
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
public class CountDownLatchTest {

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
        //  countDownLatch.countDown();减为0 就停止等待  执行finish
        countDownLatch.await();
        log.info("finish");
        // 关闭线程池
        executorService.shutdown();
    }

    private static void test(int threadNum) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(100);
        log.info("{}", threadNum);
    }
}
