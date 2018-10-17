package cn.zyblogs.example.disruptor.generate1;

import java.util.UUID;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

/**
 *  消费端
 */
public class TradeHandler implements EventHandler<Trade>, WorkHandler<Trade> {  
	  
    @Override  
    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {  
        this.onEvent(event);  
    }  
  
    @Override  
    public void onEvent(Trade event) throws Exception {  
        //这里做具体的消费逻辑   简单生成下ID
        event.setId(UUID.randomUUID().toString());
        System.out.println(event.getId());
        System.out.println(event.getPrice());
    }  
}  