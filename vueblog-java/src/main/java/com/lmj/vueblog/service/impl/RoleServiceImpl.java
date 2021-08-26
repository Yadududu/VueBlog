package com.lmj.vueblog.service.impl;


import com.lmj.vueblog.entity.Role;
import com.lmj.vueblog.mapper.RoleMapper;
import com.lmj.vueblog.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> getRolesByUserId(Long userId) {
        return roleMapper.getRolesByUserId(userId);
    }
}
