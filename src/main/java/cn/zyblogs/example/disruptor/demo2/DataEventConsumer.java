package cn.zyblogs.example.disruptor.demo2;

/**
 * @Title: DataEventConsumer.java
 * @Package cn.zyblogs.example.disruptor.demo2
 * @Description: TODO 消费者业务逻辑
 * @Author ZhangYB
 * @Version V1.0
 */
public class DataEventConsumer {

    public DataEventConsumer(DataEvent event) {

        // 业务逻辑
        System.out.println("event : " + event.getValue());

    }
}
