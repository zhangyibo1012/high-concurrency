package cn.zyblogs.example.immutable;

import cn.zyblogs.annoations.ThreadSafe;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

/**
 * @Title: ImmutableGuava.java
 * @Package cn.zyblogs.example.immutable
 * @Description: TODO 不可变对象
 * @Author ZhangYB
 * @Version V1.0
 */
@ThreadSafe
public class ImmutableGuava {

    private final static ImmutableList  list = ImmutableList.of(1,2,3);

    private final static ImmutableSet set = ImmutableSet.copyOf(list);

    private final static ImmutableMap<Integer , Integer> map = ImmutableMap.of(1,2,3,4);

    private final static ImmutableMap<Integer , Integer> map2 = ImmutableMap.<Integer , Integer>builder().put(1,2).put(3,4).put(5,6).build();

    public static void main(String[] args) {
        System.out.println(map2.get(3));
    }
}
