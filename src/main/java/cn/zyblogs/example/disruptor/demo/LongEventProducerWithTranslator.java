package cn.zyblogs.example.disruptor.demo;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;


public class LongEventProducerWithTranslator {

	private final RingBuffer<LongEvent> ringBuffer;
	public LongEventProducerWithTranslator(RingBuffer<LongEvent> ringBuffer) {
		this.ringBuffer = ringBuffer;
	}
	// 一个Translator可以看做一个事件初始化器 publicEvent方法会调用它
	// disruptor3推荐这样使用
	private static final EventTranslatorOneArg<LongEvent, ByteBuffer> TRANSLATOR =
			(event, sequeue, byteBuffer) -> {
				// 从byteBuffer中读取传过来的值
				byteBuffer.flip();
				byte[] dst = new byte[byteBuffer.limit()];
				byteBuffer.get(dst, 0, dst.length);
				byteBuffer.clear();
				// 为event赋值
				event.setValue(new String(dst));
				event.setId(sequeue);
			};
	public void sendData(ByteBuffer buffer) {
		ringBuffer.publishEvent(TRANSLATOR, buffer);
	}

}
