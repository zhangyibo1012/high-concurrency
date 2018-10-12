package cn.zyblogs.example.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.*;

/**
 * @Title: CopyOnWriteArrayListExample.java
 * @Package cn.zyblogs.example.concurrent
 * @Description: TODO 并发容器 arraylist  对应的线程安全类CopyOnWriteArrayList
 *                             HashSet 对应 CopyOnWriteArraySet
 *                             TreeSet 对应 ConcurrentSkipListSet
 *                             HashMap --> ConcurrentHashMap
 *                             TreeMap --> ConsurrentSkipListMap  k是有序的，支持更高的并发
 * @Author ZhangYB
 * @Version V1.0
 */
@Slf4j
public class CopyOnWriteArrayListExample {

    // 请求总数
    public static int clientTotal = 5000;

    /**
     *  同时并发执行的线程数
     */
    public static int threadTotal = 200;

    private static List<Integer> list = new CopyOnWriteArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0 ; i < clientTotal ; i ++){
            final int count = i ;
            executorService.execute(()->{
                try {
                    // 是否允许被执行 如果达到一定并发数 可能会阻塞
                    semaphore.acquire();
                    update(count);
                    // 释放
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }
        // countDownLatch.countDown();到0 就停止await
        countDownLatch.await();
        // 关闭线程池
        executorService.shutdown();
        log.info("size:{}",list.size() );
    }

    private static void update(int count) {
        list.add(count);
    }

}
