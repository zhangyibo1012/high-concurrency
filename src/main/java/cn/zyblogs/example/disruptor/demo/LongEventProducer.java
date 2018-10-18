package cn.zyblogs.example.disruptor.demo;

import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * @Title: LongEventProducer.java
 * @Package cn.zyblogs.example.disruptor
 * @Description: TODO 不推荐
 * @Author ZhangYB
 * @Version V1.0
 * 当用一个简单队列来发布事件的时候会牵涉更多的细节，这是因为事件对象还需要预先创建。
 * 发布事件最少需要两步：获取下一个事件槽并发布事件（发布事件的时候要使用try/finnally保证事件一定会被发布）。
 * 如果我们使用RingBuffer.next()获取一个事件槽，那么一定要发布对应的事件。
 * 如果不能发布事件，那么就会引起Disruptor状态的混乱。
 * 尤其是在多个事件生产者的情况下会导致事件消费者失速，从而不得不重启应用才能会恢复。
 */
public class LongEventProducer {

    private final RingBuffer<LongEvent> ringBuffer;

    public LongEventProducer(RingBuffer<LongEvent> ringBuffer){
        this.ringBuffer = ringBuffer;
    }

    /**
     * onData用来发布事件，每调用一次就发布一次事件
     * 它的参数会用过事件传递给消费者
     */
    public void sendData(ByteBuffer byteBuffer) {
        // ringBuffer就是用来存储数据的，具体可以看disruptor源码的数据结构，next就是获取下一个空事件索引
        long sequence = ringBuffer.next();
        try {
            //  通过索引获取空事件
            LongEvent event = ringBuffer.get(sequence);
            // 从byteBuffer中读取传过来的值
            byteBuffer.flip();
            byte[] dst = new byte[byteBuffer.limit()];
            byteBuffer.get(dst, 0, dst.length);
            byteBuffer.clear();
            // 为event赋值
            event.setValue(new String(dst));
            event.setId(sequence);

        } finally {
            // 发布事件，为确保安全，放入finally中，不会造成disruptor的混乱
            ringBuffer.publish(sequence);
        }
    }


}
