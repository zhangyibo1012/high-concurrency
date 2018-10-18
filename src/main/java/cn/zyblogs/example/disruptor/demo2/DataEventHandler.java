package cn.zyblogs.example.disruptor.demo2;

import com.lmax.disruptor.EventHandler;

/**
 * @Title: DataEventHandler.java
 * @Package cn.zyblogs.example.disruptor.demo2
 * @Description: TODO 处理器
 * @Author ZhangYB
 * @Version V1.0
 */
public class DataEventHandler implements EventHandler<DataEvent> {
    @Override
    public void onEvent(DataEvent dataEvent, long l, boolean b) throws Exception {
        new DataEventConsumer(dataEvent);
    }
}
