package cn.zyblogs.example.threadlocal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Title: ThreadLocalController.java
 * @Package cn.zyblogs.example.threadlocal
 * @Description: TODO
 * @Author ZhangYB
 * @Version V1.0
 */
@RestController
@RequestMapping(value = "threadLocal")
public class ThreadLocalController {

    @GetMapping(value = "test")
    public Long test() {
        return RequestHolder.getId();
    }
}
