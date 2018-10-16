package cn.zyblogs.example.deadlock;

import lombok.extern.slf4j.Slf4j;

/**
 * @Title: DeadLock.java
 * @Package cn.zyblogs.example.deadlock
 * @Description: TODO 死锁:当DeadLock类的对象flag==1时(td1),先锁定o1,睡眠500毫秒
 * 而td1睡眠的适合另一个flag==0的对象(td2)线程启动，先锁定o2，睡眠500毫秒
 * td1睡眠结束后需要锁定o2才能继续执行，而此时o2已经被td2锁定
 * td2睡眠结束后需要锁定o1才能继续执行，而此时o1已经被td1锁定
 * td1、td2互相等待，都需要得到对方锁定的资源才能继续执行，从而死锁
 * @Author ZhangYB
 * @Version V1.0
 */
@Slf4j
public class DeadLock implements Runnable {

    /**
     * 静态对象是类的所有对象共享的
     */
    private static Object o1 = new Object(), o2 = new Object();
    private int flag = 1;

    public static void main(String[] args) {
        DeadLock td1 = new DeadLock();
        DeadLock td2 = new DeadLock();
        td1.flag = 1;
        td2.flag = 0;
        // td1 td2都处于可执行状态 但是JVM线程调度先执行哪个线程是不确定的
        // td1的run()可能在td1的run()之前运行
        new Thread(td1).start();
        new Thread(td2).start();
    }

    @Override
    public void run() {
        log.info("flag：{}", flag);
        if (flag == 1) {
            synchronized (o1) {
                try {
                    Thread.sleep(5_00);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (o2) {
                    log.info("1");
                }
            }
        }

        if (flag == 0) {
            synchronized (o2) {
                try {
                    Thread.sleep(5_00);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (o1) {
                    log.info("0");
                }
            }
        }
    }
}
