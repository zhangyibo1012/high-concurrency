package cn.zyblogs.example.disruptor.demo2;

import com.lmax.disruptor.EventFactory;

/**
 * @Title: DataEventFactory.java
 * @Package cn.zyblogs.example.disruptor.demo2
 * @Description: TODO 数据工厂
 * @Author ZhangYB
 * @Version V1.0
 */
public class DataEventFactory implements EventFactory<DataEvent> {
    @Override
    public DataEvent newInstance() {
        return new DataEvent();
    }
}
