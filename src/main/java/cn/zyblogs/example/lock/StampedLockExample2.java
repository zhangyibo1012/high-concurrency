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
 * @Title: StampedLockExample2.java
 * @Package com.zyblogs.concurrency.juc.utils.locks
 * @Description: TODO 乐观StampedLock 替代ReentrantReadWriteLock
 * @Author ZhangYB
 * @Version V1.0
 */
public class StampedLockExample2 {
    private static final StampedLock lock = new StampedLock();

    private final static List<Long> DATA = new ArrayList<>();

    public static void main(String[] args) {

        // 线程池
        ExecutorService executor = Executors.newFixedThreadPool(10);

        Runnable readTask = () ->{
            while (true){
                read();
            }
        };

        Runnable writeTask = () ->{
            while (true){
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

    private static void read(){
        // 尝试获取乐观锁
      long stamp = lock.tryOptimisticRead();
      // 检查在获取到读锁票据后，锁有没被其他写线程排它性抢占
      if (lock.validate(stamp)){

          try {
              // 如果被抢占则获取一个共享读锁（悲观获取）
              stamp = lock.readLock();
              Optional.of(
                      DATA.stream().map(String::valueOf).collect(Collectors.joining("read", "R-", ""))
              ).ifPresent(System.out::println);

              TimeUnit.SECONDS.sleep(1);

          } catch (InterruptedException e) {
              e.printStackTrace();
          } finally {
              lock.unlockRead(stamp);
          }
      }
    }

    private static void write(){
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

