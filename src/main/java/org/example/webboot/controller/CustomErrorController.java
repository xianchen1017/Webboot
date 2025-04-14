package org.example.webboot.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        // 自定义错误页面的处理
        return "error"; // 返回自定义错误页面的视图
    }

    // 删除 getErrorPath() 方法，Spring Boot 2.3 及以后的版本不需要它
}
