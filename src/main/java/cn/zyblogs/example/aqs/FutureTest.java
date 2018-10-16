package cn.zyblogs.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @Title: FutureTest.java
 * @Package cn.zyblogs.example.aqs
 * @Description: TODO
 * @Author ZhangYB
 * @Version V1.0
 */
@Slf4j
public class FutureTest {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<String> future = executor.submit(new MyCallable());
        log.info("do something in main");
        Thread.sleep(1_000);
        // 没有执行完成会等待
        String result = future.get();
        log.info("result：{}", result);


    }

    static class MyCallable implements Callable<String> {
        @Override
        public String call() throws Exception {
            log.info("do something in callable");
            Thread.sleep(5_000);
            return "Done.";
        }
    }
}
