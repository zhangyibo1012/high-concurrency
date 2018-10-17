package cn.zyblogs.example.disruptor.demo;

import com.lmax.disruptor.EventFactory;

/**
 * @Title: LongEventFactory.java
 * @Package cn.zyblogs.example.disruptor
 * @Description: TODO 由于需要让Disruptor为我们创建事件，我们同时还声明了一个EventFactory来实例化Event对象。
 * @Author ZhangYB
 * @Version V1.0
 */
public class LongEventFactory implements EventFactory {
    @Override
    public Object newInstance() {
        return new LongEvent();
    }
}
