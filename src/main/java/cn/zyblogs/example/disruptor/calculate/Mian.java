package cn.zyblogs.example.disruptor.calculate;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.ProducerType;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

/**
 * @Title: Mian.java
 * @Package cn.zyblogs.example.disruptor.calculate
 * @Description: TODO
 * @Author ZhangYB
 * @Version V1.0
 */
public class Mian {
    public static void main(String[] args) throws InterruptedException {
         //创建ringBuffer
        RingBuffer<SingleSumEvent> ringBuffer =
                RingBuffer.create(ProducerType.MULTI,
                        () -> new SingleSumEvent(),
                        1024 * 1024,
                        new YieldingWaitStrategy());

        // 创建SequenceBarrier 平衡生产者和消费者速度
        SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();

        Phaser phaser = new Phaser(1);
        // 定义一个用于整合计算的消费者
        MergerSumEvent mergerSumEvent = new MergerSumEvent(phaser);

        WorkerPool<SingleSumEvent> workerPool = new WorkerPool<>(ringBuffer, sequenceBarrier,
                new IntEventExceptionHandler(), mergerSumEvent);

        ringBuffer.addGatingSequences(workerPool.getWorkerSequences());
        workerPool.start(Executors.newCachedThreadPool());
        final SumEventProducer p = new SumEventProducer(ringBuffer);
        Instant start = Instant.now();
        for(int i = 0 ; i < 100; i++) {
            new Thread(() -> {
                for (int j = 0; j < 5; j++) {
                    p.onData(100000000L);
                }
            }).start();
        }

        phaser.arriveAndAwaitAdvance();
        Instant end = Instant.now();
        System.out.println("500个一亿求和总共花费的时间: "+ Duration.between(start,end).toMillis() + " 毫秒,值为:   " +mergerSumEvent.getSum());
        workerPool.halt(); //通知事件(或者说消息)处理器 可以结束了（并不是马上结束!!!）
    }




    static class IntEventExceptionHandler implements ExceptionHandler<Object> {
        @Override
        public void handleEventException(Throwable ex, long sequence, Object event) {
        }

        @Override
        public void handleOnStartException(Throwable ex) {
        }

        @Override
        public void handleOnShutdownException(Throwable ex) {
        }
    }

}
