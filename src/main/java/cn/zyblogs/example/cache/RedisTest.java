package cn.zyblogs.example.cache;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * @Title: RedisConfig.java
 * @Package cn.zyblogs.example.cache
 * @Description: TODO
 * @Author ZhangYB
 * @Version V1.0
 */
public class RedisTest{

    public static void main(String[] args) throws Exception {
        jedisCluster();
    }

    public static void jedisCluster() throws Exception{

        // jedisCluster 对象 ,参数nodes是Set类型,包含若干个HostAndPort对象
        Set<HostAndPort> nodes = new HashSet<>(16);
        nodes.add(new HostAndPort("192.168.32.133" , 7001));
        nodes.add(new HostAndPort("192.168.32.133" , 7002));
        nodes.add(new HostAndPort("192.168.32.133" , 7003));
        nodes.add(new HostAndPort("192.168.32.133" , 7004));
        nodes.add(new HostAndPort("192.168.32.133" , 7005));
        nodes.add(new HostAndPort("192.168.32.133" , 7006));

        JedisCluster jedisCluster = new JedisCluster(nodes);

//        // jedisCluster操作redis集群
        jedisCluster.set("jedisCluster" , "test");
        System.err.println(jedisCluster.get("jedisCluster"));
//
//        // close
//        jedisCluster.close();

    }
}
