package cn.zyblogs.example.disruptor.demo;

import com.lmax.disruptor.EventHandler;

/**
 * @Title: LongEventHandler.java
 * @Package cn.zyblogs.example.disruptor
 * @Description: TODO 事件监听处理器
 * @Author ZhangYB
 * @Version V1.0
 */
public class LongEventHandler implements EventHandler<LongEvent> {
    @Override
    public void onEvent(LongEvent longEvent, long l, boolean b) throws Exception {
        System.out.println(longEvent.getValue());
    }
}
