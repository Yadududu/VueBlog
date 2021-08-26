package com.lmj.vueblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lmj.vueblog.entity.Role;

import java.util.List;

/**
 *  服务类
 * */
public interface RoleService extends IService<Role> {
    /**
     * 根据用户id获取用户的角色集合
     * @param userId
     * @return
     */
    List<Role> getRolesByUserId(Long userId);
}
