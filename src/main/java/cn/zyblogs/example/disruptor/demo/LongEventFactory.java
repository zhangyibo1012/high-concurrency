package cn.zyblogs.example.disruptor.demo;

import com.lmax.disruptor.EventFactory;

/**
 * @Title: LongEventFactory.java
 * @Package cn.zyblogs.example.disruptor
 * @Description: TODO 实现Disruptor提供的工厂接口，工厂方法模式创建自定义对象
 * @Author ZhangYB
 * @Version V1.0
 */
public class LongEventFactory implements EventFactory {
    @Override
    public Object newInstance() {
        return new LongEvent();
    }
}
