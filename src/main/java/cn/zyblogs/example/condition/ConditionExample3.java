package cn.zyblogs.example.condition;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * @Title: ConditionExample3.java
 * @Package com.zyblogs.concurrency.juc.utils.condition
 * @Description: TODO
 * @Author ZhangYB
 * @Version V1.0
 */
public class ConditionExample3 {

    private final static ReentrantLock lock = new ReentrantLock();

    private final static Condition PRODUCE_COND = lock.newCondition();

    private final static Condition CONSUME_COND = lock.newCondition();

    private final static LinkedList<Long> TIMESATMP_POOL = new LinkedList<>();

    private final static int MAX_CAPAITY = 100;

    public static void main(String[] args) {
        // 启5个生产线程
        IntStream.range(0, 6).forEach(ConditionExample3::beginProduce);
        IntStream.range(0, 13).forEach(ConditionExample3::beginConsume);

        while (true) {
            sleepThread(5);
            System.out.println("=================================");
//            System.out.println("PRODUCE_COND.getWaitQueueLength>" + lock.getWaitQueueLength(PRODUCE_COND));
//            System.out.println("CONSUME_COND.getWaitQueueLength>" + lock.getWaitQueueLength(CONSUME_COND));
//            System.out.println("CONSUME_COND.getWaitQueueLength>" + lock.hasWaiters(CONSUME_COND));
//            System.out.println("PRODUCE_COND.getWaitQueueLength>" + lock.hasWaiters(PRODUCE_COND));
        }
    }

    private static void beginProduce(int i) {
        new Thread(() -> {
            while (true) {
                produce();
                sleepThread(2);
            }
        }, "P-" + i).start();
    }

    private static void beginConsume(int i) {
        new Thread(() -> {
            while (true) {
                consume();
                sleepThread(2);
            }
        }, "C-" + i).start();
    }

    private static void produce() {
        try {
            lock.lock();
            while (TIMESATMP_POOL.size() >= MAX_CAPAITY) {
                PRODUCE_COND.await();
            }

            System.out.println("PRODUCE_COND.getWaitQueueLength>" + lock.getWaitQueueLength(PRODUCE_COND));
            long value = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName() + "-P- " + value);
            TIMESATMP_POOL.addLast(value);
            // 通知消费
            CONSUME_COND.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private static void consume() {
        try {
            lock.lock();
            while (TIMESATMP_POOL.isEmpty()) {
                CONSUME_COND.await();
            }

            Long value = TIMESATMP_POOL.removeFirst();
            System.out.println(Thread.currentThread().getName() + "-C- " + value);
            // 通知生产
            PRODUCE_COND.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private static void sleepThread(long sec) {
        try {
            TimeUnit.SECONDS.sleep(sec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
