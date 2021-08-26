package com.lmj.vueblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lmj.vueblog.entity.Permission;

import java.util.List;

/**
 *  服务类
 * */
public interface PermissionService extends IService<Permission> {
    /**
     * 根据角色id获取权限集合
     * @param roleId
     * @return
     */
    List<Permission> getPermissionsByRoleId(Long roleId);
}
