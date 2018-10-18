package cn.zyblogs.example.disruptor.calculate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigInteger;
import java.util.stream.LongStream;

/**
 * @Title: SumEvent.java
 * @Package cn.zyblogs.example.disruptor.calculate
 * @Description: TODO   这个类用来存储每个线程计算的值。
 * @Author ZhangYB
 * @Version V1.0
 */
@Getter
@Setter
@ToString
public class SingleSumEvent {

    private String thread;
    private  long value;

    public  void calculate(long strat , long end){
//        LongStream.rangeClosed(strat, end).sum();
        for (long i = strat; i <= end; i ++){
            value += i ;
        }

    }
    public static void main(String[] args) {
       System.out.println(LongStream.rangeClosed(1, 3).sum());

    }
}
