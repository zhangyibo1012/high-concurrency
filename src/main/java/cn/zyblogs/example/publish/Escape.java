package cn.zyblogs.example.publish;

import lombok.extern.slf4j.Slf4j;

/**
 * @Title: Escape.java
 * @Package cn.zyblogs.example.publish
 * @Description: TODO 对象溢出
 * @Author ZhangYB
 * @Version V1.0
 */
@Slf4j
public class Escape {

    private int thisCanBeEscape = 0;

    public Escape(){
        new InnerClass();
    }

    private class InnerClass{

        public InnerClass(){
            log.info("{}",Escape.this.thisCanBeEscape );
        }
    }

    public static void main(String[] args) {
        new Escape();
    }
}
