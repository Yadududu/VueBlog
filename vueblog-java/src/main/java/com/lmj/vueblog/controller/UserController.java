package com.lmj.vueblog.controller;


import com.lmj.vueblog.common.lang.Result;
import com.lmj.vueblog.entity.User;
import com.lmj.vueblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 */
@RestController
//@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Value("${project.env}")
    private String projectEnv;

//    @RequiresAuthentication
    @GetMapping("/user")
    public Result user() {
        User user = userService.getById(1);
        return Result.succ(user);
    }

    @PostMapping("/save")
    public Result save(@Validated @RequestBody User user) {
        return Result.succ(user);
    }

    @RequestMapping("/index")
    public String index() {
        return "/index";
    }

    @GetMapping("env")
    public String env() {
        return "当前环境为：" + projectEnv;
    }
}
