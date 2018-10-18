package cn.zyblogs.example.disruptor.demo;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Title: LongEventMain.java
 * @Package cn.zyblogs.example.disruptor
 * @Description: TODO
 *
 *          //BlockingWaitStrategy 是最低效的策略，但其对CPU的消耗最小并且在各种不同部署环境中能提供更加一致的性能表现
 *          WaitStrategy BLOCKING_WAIT = new BlockingWaitStrategy();
 *          //SleepingWaitStrategy 的性能表现跟BlockingWaitStrategy差不多，对CPU的消耗也类似，但其对生产者线程的影响最小，适合用于异步日志类似的场景
 *          WaitStrategy SLEEPING_WAIT = new SleepingWaitStrategy();
 *          //YieldingWaitStrategy 的性能是最好的，适合用于低延迟的系统。在要求极高性能且事件处理线数小于CPU逻辑核心数的场景中，推荐使用此策略；例如，CPU开启超线程的特性
 *          WaitStrategy YIELDING_WAIT = new YieldingWaitStrategy();
 *
 * @Author ZhangYB
 * @Version V1.0
 */
public class LongEventMain {
    public static void main(String[] args) throws InterruptedException {
        //创建缓冲池
        final ExecutorService executor = Executors.newCachedThreadPool();
        // 创建event工厂
        LongEventFactory factory = new LongEventFactory();
        // 创建缓冲区大小 2的n次方
        int ringBufferSize = 1024 * 1024;
        // 创建disruptor对象
        // eventFactory :传入实现了EventFactory接口的工厂类对象
        // ringBufferSize: 用来存储数据的， 值为 2^n
        // executor线程池 进行Disruptor内部的数据接收处理调度
        // ProducerType: SINGLE(表示当前的生产者只有一个)和MULTI(表示当前的生产者有多个).
        // waitStrategy ：YieldingWaitStrategy是一种策略 关于生产和消费的调度
        Disruptor<LongEvent> disruptor = new
                Disruptor<LongEvent>(factory, ringBufferSize, executor,ProducerType.SINGLE,new YieldingWaitStrategy());
        //关联处理器，也就是消费者，连接消费事件方法
        disruptor.handleEventsWith(new LongEventHandler());
        disruptor.start();
        // 在Disruptor中，真正存储数据的核心叫做RingBuffer，从Disruptor实例拿到它，然后把数据生产出来，把数据加入到RingBuffer的实例对象中即可。
        final RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
//        LongEventProducer producer = new LongEventProducer(ringBuffer);
        LongEventProducerWithTranslator producer = new LongEventProducerWithTranslator(ringBuffer);

        // 定义一个新的字节缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        for(long l = 0; l<100; l++){
            byteBuffer.put(new String("生产者" + l + "发布消息").getBytes());
            producer.sendData(byteBuffer);
            Thread.sleep(1000);
        }

        disruptor.shutdown();//关闭 disruptor，方法会堵塞，直至所有的事件都得到处理；
        // 关闭 disruptor 使用的线程池；如果需要的话，必须手动关闭， disruptor 在 shutdown 时不会自动关闭；
        executor.shutdown();
    }
}
