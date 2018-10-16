package cn.zyblogs.example.atomic;

import cn.zyblogs.annoations.ThreadSafe;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @Title: AtomicReferenceTest.java
 * @Package cn.zyblogs.example.atomic
 * @Description: TODO
 * @Author ZhangYB
 * @Version V1.0
 */
@ThreadSafe
@Slf4j
public class AtomicIntegerFieldUpdaterTest {


    private static AtomicIntegerFieldUpdater<AtomicIntegerFieldUpdaterTest> updater = AtomicIntegerFieldUpdater.newUpdater(AtomicIntegerFieldUpdaterTest.class, "count");
    private static AtomicIntegerFieldUpdaterTest atomicIntegerUpdater = new AtomicIntegerFieldUpdaterTest();
    @Getter
    public volatile int count = 100;

    public static void main(String[] args) {
        // volatile  非static修饰的字段可以更新  如果是100 就更新为120
        if (updater.compareAndSet(atomicIntegerUpdater, 100, 120)) {
            log.info("update success 1 ,{}", atomicIntegerUpdater.getCount());
        }

        if (updater.compareAndSet(atomicIntegerUpdater, 100, 120)) {
            log.info("update success 2 ,{}", atomicIntegerUpdater.getCount());
        } else {
            log.info("update failed.{}", atomicIntegerUpdater.getCount());
        }


    }
}
