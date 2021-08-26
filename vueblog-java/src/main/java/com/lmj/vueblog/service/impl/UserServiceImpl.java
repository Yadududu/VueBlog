package com.lmj.vueblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lmj.vueblog.common.salt.ShiroConstant;
import com.lmj.vueblog.entity.User;
import com.lmj.vueblog.mapper.UserMapper;
import com.lmj.vueblog.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lmj.vueblog.util.SaltUtil;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static org.springframework.util.Assert.notNull;

/**
 * <p>
 *  服务实现类
 * </p>
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void register(User user) {
        List<User> username = userMapper.selectList(new QueryWrapper<User>().eq("username", user.getUsername()));
        Assert.isTrue(!(username.size()>0), "用户名已经存在");

        // 生成随机盐
        String salt = SaltUtil.getSalt(ShiroConstant.SALT_LENGTH);
        // 保存随机盐
        user.setSalt(salt);
        // 生成密码
        Md5Hash password = new Md5Hash(user.getPassword(), salt, ShiroConstant.HASH_ITERATORS);
        // 保存密码
        user.setPassword(password.toHex());

        userMapper.insert(user);
        userMapper.setUserRole(user.getId());
    }
}
