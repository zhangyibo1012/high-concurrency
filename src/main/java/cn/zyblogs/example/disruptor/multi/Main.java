package cn.zyblogs.example.disruptor.multi;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

public class Main {
	
	public static void main(String[] args) throws Exception {

		// 处理器数
		System.err.println( Runtime.getRuntime().availableProcessors());

		//创建ringBuffer
		RingBuffer<Order> ringBuffer = 
				RingBuffer.create(ProducerType.MULTI, 
						new EventFactory<Order>() {  
				            @Override  
				            public Order newInstance() {  
				                return new Order();  
				            }  
				        }, 
				        1024 * 1024, 
						new YieldingWaitStrategy());
		
		SequenceBarrier barriers = ringBuffer.newBarrier();

		// 3个消费端去消费数据
		Consumer[] consumers = new Consumer[3];
		for(int i = 0; i < consumers.length; i++){
			consumers[i] = new Consumer("c" + i);
		}
		
		WorkerPool<Order> workerPool = 
				new WorkerPool<Order>(ringBuffer, 
						barriers, 
						new IntEventExceptionHandler(),
						consumers);

		// 消费下标给生产者
        ringBuffer.addGatingSequences(workerPool.getWorkerSequences());

		final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		workerPool.start(executor);

		// 100个生产者生产数据 100 * 100
//		Phaser phaser = new Phaser(1);
		final CountDownLatch latch = new CountDownLatch(1);
        for (int i = 0; i < 100; i++) {  
        	final Producer p = new Producer(ringBuffer);
        	new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						latch.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					for(int j = 0; j < 100; j ++){
						p.onData(UUID.randomUUID().toString());
					}
				}
			}).start();
        }
        Thread.sleep(2000);
        System.out.println("---------------开始生产-----------------");
        latch.countDown();
//		phaser.arriveAndAwaitAdvance();
        Thread.sleep(5000);
        System.out.println("总数:" + consumers[0].getCount() );

	}
	
	static class IntEventExceptionHandler implements ExceptionHandler {  
	    @Override
		public void handleEventException(Throwable ex, long sequence, Object event) {}
	    @Override
		public void handleOnStartException(Throwable ex) {}
	    @Override
		public void handleOnShutdownException(Throwable ex) {}
	} 
}
