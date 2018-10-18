package cn.zyblogs.example.disruptor.calculate;

import com.lmax.disruptor.RingBuffer;

/**
 * @Title: SumEventProducer.java
 * @Package cn.zyblogs.example.disruptor.calculate
 * @Description: TODO 模拟外部磁盘数据或者网络传输过来的数据，并进行发布，是一个事件源，每次都触发，自己受到调用
 * @Author ZhangYB
 * @Version V1.0
 */
public class SumEventProducer {

    private final RingBuffer<SingleSumEvent> ringBuffer;
    public SumEventProducer(RingBuffer<SingleSumEvent> ringBuffer) {	     this.ringBuffer = ringBuffer;
    }

    public void onData(long sum) {
        long sequence = ringBuffer.next();
        try {
        SingleSumEvent singleSumEvent = ringBuffer.get(sequence);
        singleSumEvent.calculate(1, sum);
        singleSumEvent.setValue(singleSumEvent.getValue());
        singleSumEvent.setThread(Thread.currentThread().getName());
    } finally {			//发布事件
        ringBuffer.publish(sequence);
    }
    }


}
