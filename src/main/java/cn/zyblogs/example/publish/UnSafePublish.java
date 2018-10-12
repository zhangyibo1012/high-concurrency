package cn.zyblogs.example.publish;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * @Title: UnSafePublish.java
 * @Package cn.zyblogs.example.publish
 * @Description: TODO 发布对象
 * @Author ZhangYB
 * @Version V1.0
 */
@Slf4j
public class UnSafePublish {

    private String[]  states = {"a" , "b" , "c"};

    public String[] getStates() {
        return states;
    }

    public static void main(String[] args) {
        UnSafePublish unSafePublish = new UnSafePublish();
        log.info("{}",Arrays.toString(unSafePublish.getStates()));

        unSafePublish.getStates()[0] = "d";
        log.info("{}",Arrays.toString(unSafePublish.getStates()));
    }
}
