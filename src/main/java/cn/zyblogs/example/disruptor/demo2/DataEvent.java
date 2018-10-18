package cn.zyblogs.example.disruptor.demo2;

import lombok.Getter;
import lombok.Setter;

/**
 * @Title: DataEvent.java
 * @Package cn.zyblogs.example.disruptor.demo2
 * @Description: TODO 携带数据
 * @Author ZhangYB
 * @Version V1.0
 */
@Getter
@Setter
public class DataEvent {
    private long value;
}
