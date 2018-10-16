package cn.zyblogs.example.disruptor;

import lombok.Getter;
import lombok.Setter;

/**
 * @Title: LongEvent.java
 * @Package cn.zyblogs.example.disruptor
 * @Description: TODO 生产者传递一个long类型的值给消费者，而消费者消费这个数据的方式仅仅是把它打印出来
 * @Author ZhangYB
 * @Version V1.0
 */
@Getter
@Setter
public class LongEvent {

    private long value;

}
