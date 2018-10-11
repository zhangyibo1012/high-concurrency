package cn.zyblogs.atomic;

import cn.zyblogs.annoations.ThreadSafe;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Title: AtomicExample4.java
 * @Package cn.zyblogs.atomic
 * @Description: TODO
 * @Author ZhangYB
 * @Version V1.0
 */
@ThreadSafe
@Slf4j
public class AtomicExample5 {


    private static AtomicIntegerFieldUpdater<AtomicExample5> updater = AtomicIntegerFieldUpdater.newUpdater(AtomicExample5.class, "count");

    @Getter
    public volatile int count = 100;

    private static AtomicExample5 example5 = new AtomicExample5();

    public static void main(String[] args) {
        if (updater.compareAndSet(example5, 100, 120)){
            log.info("update success 1 ,{}", example5.getCount());
        }

        if (updater.compareAndSet(example5, 100, 120)){
            log.info("update success 2 ,{}", example5.getCount());
        }else {
            log.info("update failed  ,{}", example5.getCount());
        }


    }
}
