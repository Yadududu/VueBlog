package com.lmj.vueblog.config.shiro;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lmj.vueblog.entity.Permission;
import com.lmj.vueblog.entity.Role;
import com.lmj.vueblog.entity.User;
import com.lmj.vueblog.service.PermissionService;
import com.lmj.vueblog.service.RoleService;
import com.lmj.vueblog.service.UserService;
import com.lmj.vueblog.util.JwtUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 进行登录或者权限校验的逻辑,核心
 *  AccountRealm是shiro进行登录或者权限校验的逻辑所在，这是核心，我们需要重写3个方法，分别是
 *      supports：为了让realm支持jwt的凭证校验
 *      doGetAuthorizationInfo：权限校验
 *      doGetAuthenticationInfo：登录认证校验
 * */
//@Component
public class AccountRealm extends AuthorizingRealm {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    PermissionService permissionService;

    /**
     * 判断token是否是JwtToken
     * */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 权限校验
     * */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("执行了=>doGetAuthorizationInfo");

        // 获取主身份信息
        AccountProfile profile = (AccountProfile) principals.getPrimaryPrincipal();
        User user = userService.getOne(new QueryWrapper<User>().eq("username",profile.getUsername()));
        List<Role> roles = roleService.getRolesByUserId(user.getId());
        if(!CollectionUtils.isEmpty(roles)){
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            roles.forEach(role -> {
                simpleAuthorizationInfo.addRole(role.getName());
                List<Permission> permissions = permissionService.getPermissionsByRoleId(role.getId());
                if(!CollectionUtils.isEmpty(permissions)){
                    permissions.forEach(permission -> {
                        simpleAuthorizationInfo.addStringPermission(permission.getName());
                    });
                }
            });
            return simpleAuthorizationInfo;
        }
        return null;
    }

    /**
     * 登录认证校验
     * */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了=>doGetAuthenticationInfo");

//        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
//        User user = userService.getOne(new QueryWrapper<User>().eq("username", userToken.getUsername()));
//
//        if(!ObjectUtils.isEmpty(user)){
//            if (user.getStatus() == -1) {
//                throw new LockedAccountException("账户已被锁定");
//            }
//            //AccountProfile，为了登录成功之后返回的一个用户信息的载体
//            AccountProfile profile = new AccountProfile();
//            BeanUtil.copyProperties(user, profile);
//
//            // 密码认证
//            return new SimpleAuthenticationInfo(profile, user.getPassword(), new CustomerByteSource(user.getSalt()), getName());
//        }
//        return null;

        JwtToken jwtToken = (JwtToken) token;

        String userId = jwtUtils.getClaimByToken((String) jwtToken.getPrincipal()).getSubject();
        User user = userService.getById(Long.valueOf(userId));
        if (user == null) {
            throw new UnknownAccountException("账户不存在");
        }
        if (user.getStatus() == -1) {
            throw new LockedAccountException("账户已被锁定");
        }
        //AccountProfile，为了登录成功之后返回的一个用户信息的载体
        AccountProfile profile = new AccountProfile();
        BeanUtil.copyProperties(user, profile);
        // Token认证,这里不是标准写法,第一个参数应该填token,不然设置缓存后,登出删除不了信息
        return new SimpleAuthenticationInfo(profile, jwtToken.getCredentials(), getName());
    }
}
