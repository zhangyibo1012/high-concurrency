package cn.zyblogs.example.disruptor.demo;

import com.lmax.disruptor.EventHandler;

/**
 * @Title: LongEventHandler.java
 * @Package cn.zyblogs.example.disruptor
 * @Description: TODO 事件的处理器，也就是消费者，这里模拟的是将数据打印处理
 * @Author ZhangYB
 * @Version V1.0
 */
public class LongEventHandler implements EventHandler<LongEvent> {
    @Override
    public void onEvent(LongEvent longEvent, long sequence, boolean bool) throws Exception {
        System.out.println("LongEventHandler(消费者):  " + longEvent.toString() + ", sequence= " + sequence + ",bool=" + bool);

    }
}
