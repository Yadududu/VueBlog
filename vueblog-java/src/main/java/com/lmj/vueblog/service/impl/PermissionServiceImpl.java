package com.lmj.vueblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lmj.vueblog.entity.Permission;
import com.lmj.vueblog.mapper.PermissionMapper;
import com.lmj.vueblog.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> getPermissionsByRoleId(Long roleId) {
        return permissionMapper.getPermissionsByRoleId(roleId);
    }
}