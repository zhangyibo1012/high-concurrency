package cn.zyblogs.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Title: CyclicBarrierTest.java
 * @Package cn.zyblogs.example.aqs
 * @Description: TODO countdownlatch : 计数器只可以使用一次；实现一个或n个线程完成其它操作才可以继续往下执行
 * CyclicBarrier 计数器可以使用reset重置循环使用 ; 实现了多个线程之间的等待
 * @Author ZhangYB
 * @Version V1.0
 */
@Slf4j
public class CyclicBarrierTest3 {

    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(5, () -> {
        log.info("callback is running");
    });

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++) {
            final int threadNum = i;
            Thread.sleep(1_000);
            executor.execute(() -> {
                try {
                    race(threadNum);
                } catch (Exception e) {
                    log.info("exception", e);
                }
            });
        }
        executor.shutdown();
    }

    private static void race(int threadNum) {
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("{} is ready .", threadNum);
        try {
            cyclicBarrier.await(2_000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.warn("BarrierException", e);
        }


        log.info("{} continue ", threadNum);
    }
}
