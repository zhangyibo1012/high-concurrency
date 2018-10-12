package cn.zyblogs.example.commonunsafe;

import cn.zyblogs.annoations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.Vector;
import java.util.stream.IntStream;

/**
 * @Title: vectorExample.java
 * @Package cn.zyblogs.example.commonunsafe
 * @Description: TODO vector 在有些情况下操作顺序的差异也会出现线程不安全的
 * @Author ZhangYB
 * @Version V1.0
 */
@NotThreadSafe
@Slf4j
public class VectorExample {

    private static Vector<Integer> vector = new Vector<>();

    public static void main(String[] args) {
        while (true){
            IntStream.range(0,10 ).forEach((i)->vector.add(i));

            Thread t1 = new Thread(() -> {
                for (int i = 0 ; i < vector.size(); i ++){
                    vector.remove(i);
                }
            });

            Thread t2 = new Thread(() -> {
                for (int i = 0 ; i < vector.size(); i ++){
                    vector.get(i);
                }
            });
            t1.start();
            t2.start();

        }
    }
}
