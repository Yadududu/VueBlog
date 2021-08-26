package com.lmj.vueblog.config.shiro;

import lombok.Data;

import java.io.Serializable;

/**
 * 重新封装一个user类返回,避免出现密码等敏感信息
 * */
@Data
public class AccountProfile implements Serializable {

    private Long id;

    private String username;

    private String avatar;

    private String email;

}
