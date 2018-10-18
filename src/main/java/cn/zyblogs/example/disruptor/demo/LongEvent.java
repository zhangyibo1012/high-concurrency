package cn.zyblogs.example.disruptor.demo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Title: LongEvent.java
 * @Package cn.zyblogs.example.disruptor
 * @Description: TODO  用来传递数据对象
 * @Author ZhangYB
 * @Version V1.0
 */
@Getter
@Setter
@ToString
public class LongEvent {

    private long id;
    private String value;

}
