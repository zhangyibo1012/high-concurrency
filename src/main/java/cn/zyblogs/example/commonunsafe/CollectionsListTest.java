package cn.zyblogs.example.commonunsafe;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @Title: CollectionsListTest.java
 * @Package cn.zyblogs.example.commonunsafe
 * @Description: TODO Collections.synchronizedList安全
 * @Author ZhangYB
 * @Version V1.0
 */
@Slf4j
public class CollectionsListTest {

    // 请求总数
    public static int clientTotal = 6000;

    /**
     * 同时并发执行的线程数
     */
    public static int threadTotal = 200;

    /**
     * 同步容器
     */
    private static List<Integer> list = Collections.synchronizedList(Lists.newArrayList());

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal; i++) {
            final int count = i;
            executorService.execute(() -> {
                try {
                    // 是否允许被执行 如果达到一定并发数 可能会临时阻塞
                    semaphore.acquire();
                    update(count);
                    // 释放
                    semaphore.release();
                } catch (InterruptedException ex) {
                    log.error("exception", ex);
                }
                // 每执行一次减1
                countDownLatch.countDown();
            });
        }

        // 减到0停止await
        countDownLatch.await();
        // 关闭线程池
        executorService.shutdown();
        log.info("size:{}", list.size());
    }

    private static void update(int count) {
        list.add(count);
    }
}
