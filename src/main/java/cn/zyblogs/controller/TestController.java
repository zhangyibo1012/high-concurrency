package cn.zyblogs.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Title: TestController.java
 * @Package cn.zyblogs.controller
 * @Description: TODO
 * @Author ZhangYB
 * @Version V1.0
 */
@RestController
@Slf4j
public class TestController {

    @GetMapping(value = "test")
    public String test() {
        return "test";
    }
}
