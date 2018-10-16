package cn.zyblogs.example.condition;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Title: ConditionExample.java
 * @Package com.zyblogs.concurrency.juc.utils.condition
 * @Description: TODO Condition是wait notifly等的替代
 * 2个线程 一个线程负责对数据的自增 每自增相当于生产了一次 产生了数据
 * 另外一个线程把它打印出来 相当于消费
 * 生产的数据没有被消费之前 不能在生产
 * 消费之后发现没有新的数据生成出来 不能去消费
 * @Author ZhangYB
 * @Version V1.0
 */
public class ConditionExample1 {

    /**
     * 非公平的锁
     */
    private final static ReentrantLock lock = new ReentrantLock(false);

    /**
     * 通过ReentrantLock获取condition
     */
    private final static Condition condition = lock.newCondition();

    private static int data = 0;

    private static volatile boolean noUse = true;

    private static void buildData() {
        try {
            lock.lock();
            // 判断数据是否被使用
            while (noUse) {
                // 数据未被使用  停下来 等待消费
                condition.await();
            }

            data++;
            Optional.of("P: " + data).ifPresent(System.out::println);
            TimeUnit.SECONDS.sleep(1);
            noUse = true;
            // 发一个通知 生产完毕
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private static void useData() {
        try {
            lock.lock();
            while (!noUse) {
                condition.await();
            }

//            TimeUnit.SECONDS.sleep(1);
            Optional.of("C: " + data).ifPresent(System.out::println);
            noUse = false;
            // 发一个通知 生产完毕
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {

        new Thread(() -> {
            while (true) {
                buildData();
            }
        }).start();

        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                while (true) {
                    useData();
                }
            }).start();
        }
    }
}
