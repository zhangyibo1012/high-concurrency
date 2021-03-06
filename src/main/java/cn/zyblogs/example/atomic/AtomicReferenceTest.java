package cn.zyblogs.example.atomic;

import cn.zyblogs.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @Title: AtomicReferenceTest.java
 * @Package cn.zyblogs.example.atomic
 * @Description: TODO
 * @Author ZhangYB
 * @Version V1.0
 */
@ThreadSafe
@Slf4j
public class AtomicReferenceTest {

    private static AtomicReference<Integer> count = new AtomicReference<>(0);

    public static void main(String[] args) {
        count.compareAndSet(0, 2);
        count.compareAndSet(0, 1);
        count.compareAndSet(1, 3);
        count.compareAndSet(2, 4);
        count.compareAndSet(3, 5);
        log.info("count:{}", count.get());
    }
}
