package cn.zyblogs.example.condition;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Title: ConditionExample2.java
 * @Package com.zyblogs.concurrency.juc.utils.condition
 * @Description: TODO
 * @Author ZhangYB
 * @Version V1.0
 */
public class ConditionExample2 {
    /**
     *  是否公平的锁 true/false
     */
    private final static ReentrantLock lock = new ReentrantLock(true);

    /**
     *  通过ReentrantLock获取condition
     */
    private final static Condition condition =  lock.newCondition();

    private static int data = 0;

    private static volatile boolean noUse = true;

    private static void buildData(){
        try {
            lock.lock();
            // 判断数据是否被使用
            while (noUse){
                // 数据未被使用  停下来 等待消费
                condition.await();
            }

            data ++;
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

    private static void useData(){
        try {
            lock.lock();
            while (!noUse){
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

    /**
     *  1.不使用condition ？？？
     *  2.the producer get the lock but invoke await method and not jump out the lock statement block why the consumer can get the lock still？
     *  3.not use the lock only use the condition？
     * @param args
     */
    public static void main(String[] args) {

        new Thread(() -> {
            while (true){
                buildData();
            }
        }).start();

        for (int i = 0; i < 2 ; i ++) {
            new Thread(() -> {
                while (true) {
                    useData();
                }
            }).start();
        }
    }
}

