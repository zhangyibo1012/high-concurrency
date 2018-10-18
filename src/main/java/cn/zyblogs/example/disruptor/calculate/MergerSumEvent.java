package cn.zyblogs.example.disruptor.calculate;

import com.lmax.disruptor.WorkHandler;

import java.math.BigInteger;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Phaser;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Title: MergerSumEvent.java
 * @Package cn.zyblogs.example.disruptor.calculate
 * @Description: TODO 用来把每个生产者计算出来的值进行累加
 * @Author ZhangYB
 * @Version V1.0
 */
public class MergerSumEvent implements WorkHandler<SingleSumEvent> {
    private volatile BigInteger sum = new BigInteger("0");
    private volatile AtomicInteger count = new AtomicInteger(1);

    Phaser phaser;
    public MergerSumEvent(Phaser latch) {
        this.phaser = latch;
        phaser.register();
    }

    // 每个SingleSumEvent 线程计算出来的值交给MergerSumEvent来累加
    @Override
    public void onEvent(SingleSumEvent singleSumEvent) throws Exception {
        sum = sum.add(new BigInteger(singleSumEvent.getValue()+""));
        System.out.println("线程"+singleSumEvent.getThread() +"  " + count + "个一亿求和的结果为:  " + sum );
        count.incrementAndGet();
        if(count.intValue() == 501) {
            phaser.arriveAndAwaitAdvance();
        }
    }
    public BigInteger getSum() {
        return sum;
    }

}
