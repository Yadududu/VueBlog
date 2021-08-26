package com.lmj.vueblog.service;

import com.lmj.vueblog.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *  服务类
 * */
public interface UserService extends IService<User> {
    /**
     * 用户注册
     * @param user
     */
    void register(User user);
}
