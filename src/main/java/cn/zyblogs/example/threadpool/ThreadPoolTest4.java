package cn.zyblogs.example.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Title: ThreadPoolTest.java
 * @Package cn.zyblogs.example.threadpool
 * @Description: TODO
 * @Author ZhangYB
 * @Version V1.0
 */
@Slf4j
public class ThreadPoolTest4 {
    public static void main(String[] args) {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);

//        for (int i = 0 ; i < 10 ; i ++){
//            final int index = i;
//            executorService.execute(() -> log.info("task:{}", index));
//        }

        executorService.schedule(() -> log.warn("schedule run"), 3, TimeUnit.SECONDS);

        /**
         *  延迟1秒 每隔3秒执行一次 一直执行
         */
        executorService.scheduleAtFixedRate(() -> log.info("schedule run" ), 1, 3, TimeUnit.SECONDS);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                log.warn("time run" );
            }
        }, new Date(), 5 * 1_000);
//        executorService.shutdown();
    }
}
