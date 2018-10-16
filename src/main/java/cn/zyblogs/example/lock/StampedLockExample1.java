package cn.zyblogs.example.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;
import java.util.stream.Collectors;

/**
 * @Title: StampedLockExample.java
 * @Package com.zyblogs.concurrency.juc.utils.locks
 * @Description: TODO 悲观StampedLock 替代ReentrantReadWriteLock
 * @Author ZhangYB
 * @Version V1.0
 */
public class StampedLockExample1 {

    /**
     * ReentrantLock VS Synchronized
     * <p>
     * ReentrantReadWriteLock
     * 100 threads
     * 99 threads need read lock
     * 1  threads need write lock
     *
     * @param args
     */

    private static final StampedLock lock = new StampedLock();

    private final static List<Long> DATA = new ArrayList<>();

    public static void main(String[] args) {

        // 线程池
        final ExecutorService executor = Executors.newFixedThreadPool(10);

        Runnable readTask = () -> {
            for (; ; ) {
                read();
            }
        };

        Runnable writeTask = () -> {
            for (; ; ) {
                write();
            }
        };

        executor.submit(readTask);
        executor.submit(readTask);
        executor.submit(readTask);
        executor.submit(readTask);
        executor.submit(readTask);
        executor.submit(readTask);
        executor.submit(readTask);
        executor.submit(readTask);
        executor.submit(readTask);
        executor.submit(writeTask);
    }

    private static void read() {
        long stamped = -1;
        try {
            stamped = lock.readLock();
            Optional.of(DATA.stream().map(String::valueOf).collect(Collectors.joining("读取数据", "R-", ""))).ifPresent(System.out::println);
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlockRead(stamped);
        }
    }

    private static void write() {
        long stamp = -1;
        try {
            stamp = lock.writeLock();
            long millis = System.currentTimeMillis();
            DATA.add(millis);
            System.out.println("写入数据:" + millis);
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlockWrite(stamp);
            System.out.println("释放写锁");
        }
    }
}
