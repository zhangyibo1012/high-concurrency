package cn.zyblogs.example.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @Title: AtomicStampReferenceTest.java
 * @Package cn.zyblogs.example.atomic
 * @Description: TODO CAS轻量级锁：带来一个严重问题，ABA问题。解决方案AtomicStampedReference
 * @Author ZhangYB
 * @Date 2018-10-11 23:50
 * @Version V1.0
 */
public class AtomicStampReferenceTest {

    private static AtomicStampedReference<Integer> atomicRef = new
            AtomicStampedReference<>(100, 0);

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);

                // 把100变成101 0  1
                boolean success = atomicRef.compareAndSet(100, 101, atomicRef.getStamp(), atomicRef.getStamp() + 1);
                System.out.println(success);
                // 把101变成100 1  2
                success = atomicRef.compareAndSet(101, 100, atomicRef.getStamp(), atomicRef.getStamp() + 1);
                System.out.println(success);
                System.out.println("t1 atomicRef.getStamp() " + atomicRef.getStamp());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {

                int stamp = atomicRef.getStamp();
                System.out.println(" t2 Before sleep stamp:" + stamp);
                TimeUnit.SECONDS.sleep(2);
                // 把100变成101 0  1
                boolean success = atomicRef.compareAndSet(100, 101, stamp, stamp + 1);
                System.out.println("t2" + success);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}

