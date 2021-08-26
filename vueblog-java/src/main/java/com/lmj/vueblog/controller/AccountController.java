package com.lmj.vueblog.controller;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lmj.vueblog.common.dto.LoginDto;
import com.lmj.vueblog.common.lang.Result;
import com.lmj.vueblog.common.salt.ShiroConstant;
import com.lmj.vueblog.entity.Permission;
import com.lmj.vueblog.entity.Role;
import com.lmj.vueblog.entity.User;
import com.lmj.vueblog.service.PermissionService;
import com.lmj.vueblog.service.RoleService;
import com.lmj.vueblog.service.UserService;
import com.lmj.vueblog.util.JwtUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class AccountController {

    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    RoleService roleService;

    @Autowired
    PermissionService permissionService;

    /**
     * 用户登录
     * */
    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response) {
        System.out.println("执行了=>login");

//        // 获取当前的用户
//        Subject subject = SecurityUtils.getSubject();
//        // 封装用户的登录数据
//        UsernamePasswordToken token = new UsernamePasswordToken(loginDto.getUsername(),loginDto.getPassword());
//        // 执行登录方法，如果没有异常就说明oK,这里是交给Realm验证
//        try{
//            subject.login(token);// 执行登录方法，如果没有异常就说明oK,这里会跳到realm里面认证
//        }catch (IncorrectCredentialsException e) {
//            throw new IncorrectCredentialsException("密码错误");
//        }catch (UnknownAccountException e) {
//            throw new UnknownAccountException("账号错误");
//        }catch (AccountException e) {
//            throw new AccountException("登陆失败");
//        }
//        // 获取认证后的用户
//        AccountProfile user = (AccountProfile) subject.getPrincipal();
//        // 封装成自定义的Token
//        String jwt = jwtUtils.generateToken(user.getId());
//
//        response.setHeader("Authorization", jwt);
//        response.setHeader("Access-control-Expose-Headers", "Authorization");
//
//        return Result.succ(MapUtil.builder()
//                .put("id", user.getId())
//                .put("username", user.getUsername())
//                .put("avatar", user.getAvatar())
//                .put("email", user.getEmail())
//                .map()
//        );


        User user = userService.getOne(new QueryWrapper<User>().eq("username", loginDto.getUsername()));
        Assert.notNull(user, "用户不存在");
        if(!user.getPassword().equals((new Md5Hash(loginDto.getPassword(), user.getSalt(), ShiroConstant.HASH_ITERATORS)).toHex())){
            return Result.fail("密码不正确");
        }
        String jwt = jwtUtils.generateToken(user.getId());

        response.setHeader("Authorization", jwt);
        response.setHeader("Access-control-Expose-Headers", "Authorization");

        // 查询权限
        List<Role> roles = roleService.getRolesByUserId(user.getId());
        if(!CollectionUtils.isEmpty(roles)){
            user.setRoles(roles);
            for(int i=0;i<roles.size();i++){
                List<Permission> permissions = permissionService.getPermissionsByRoleId(roles.get(i).getId());
                if(!CollectionUtils.isEmpty(permissions)){
                    user.getRoles().get(i).setPermissions(permissions);
                }
            }
        }
        System.out.println(user.getRoles());

        return Result.succ(MapUtil.builder()
                .put("id", user.getId())
                .put("username", user.getUsername())
                .put("avatar", user.getAvatar())
                .put("email", user.getEmail())
                .put("role", user.getRoles())
                .map()
        );
    }

    @RequiresAuthentication
    @GetMapping("/logout")
    public Result logout() {
        System.out.println("执行了=>logout");

        SecurityUtils.getSubject().logout();
        return Result.succ(null);
    }

    /**
     * 用户注册
     */
    @RequestMapping("/register")
    public Result register(@Validated @RequestBody User user){
        System.out.println("执行了=>register");

        try {
            userService.register(user);
            return Result.succ(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("用户名已经存在");
    }
}
