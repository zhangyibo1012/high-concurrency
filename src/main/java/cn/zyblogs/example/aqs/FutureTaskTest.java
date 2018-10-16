package cn.zyblogs.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Title: FutureTaskTest.java
 * @Package cn.zyblogs.example.aqs
 * @Description: TODO OK
 * @Author ZhangYB
 * @Version V1.0
 */
@Slf4j
public class FutureTaskTest {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            log.info("do something in callable");
            Thread.sleep(5_000);
            return "Done.";
        });

        new Thread(futureTask).start();
        log.info("do something in main");
        Thread.sleep(1_000);
        String result = futureTask.get();
        log.info("result：{}", result);
    }
}
