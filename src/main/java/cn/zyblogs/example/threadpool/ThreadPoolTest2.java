package cn.zyblogs.example.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Title: ThreadPoolTest.java
 * @Package cn.zyblogs.example.threadpool
 * @Description: TODO
 * @Author ZhangYB
 * @Version V1.0
 */
@Slf4j
public class ThreadPoolTest2 {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 10; i++) {
            final int index = i;
            executorService.execute(() ->
                    log.info("task:{}", index));
        }

        executorService.shutdown();
    }
}
