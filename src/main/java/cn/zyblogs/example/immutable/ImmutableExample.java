package cn.zyblogs.example.immutable;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Map;

/**
 * @Title: ImmutableExample.java
 * @Package cn.zyblogs.example.immutable
 * @Description: TODO
 * @Author ZhangYB
 * @Version V1.0
 */
@Slf4j
public class ImmutableExample {

 private  static Map<Integer , Integer> map =  Maps.newHashMap();

 static {
     map.put(1,2 );
     map.put(3,4 );
     map.put(5,6 );
     // 不可变对象
     map = Collections.unmodifiableMap(map);
 }

    public static void main(String[] args) {
        map.put(1,3 );
        log.info("{}",map.get(1) );
    }

}
