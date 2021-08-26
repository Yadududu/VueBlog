package com.lmj.vueblog;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lmj.vueblog.common.salt.ShiroConstant;
import com.lmj.vueblog.config.shiro.AccountProfile;
import com.lmj.vueblog.entity.Permission;
import com.lmj.vueblog.entity.Role;
import com.lmj.vueblog.entity.User;
import com.lmj.vueblog.mapper.UserMapper;
import com.lmj.vueblog.service.PermissionService;
import com.lmj.vueblog.service.RoleService;
import com.lmj.vueblog.service.UserService;
import com.lmj.vueblog.util.SaltUtil;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class VueblogApplicationTests {

    @Autowired
    UserMapper userMapper;
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    PermissionService permissionService;

    @Test
    void contextLoads() {
        System.out.println(SecureUtil.md5("111"));
    }

    /**
     *  测试查询数据库
     * */
    @Test
    void testSql() {
        QueryWrapper<User> qw = new QueryWrapper<>();
        Map<String,Object> tj = new HashMap<>();
        tj.put("username","aaa");
        qw.allEq(tj);
        List<User> users = userMapper.selectList(qw);
        System.out.println(users);
    }

    /**
     *  生成测试密码
     * */
    @Test
    void testPassword(){
        // 生成随机盐
        String salt = SaltUtil.getSalt(ShiroConstant.SALT_LENGTH);
        System.out.println("salt:"+salt);
        // 生成密码
        Md5Hash password = new Md5Hash("111111", salt, ShiroConstant.HASH_ITERATORS);
        System.out.println("password:"+password);
    }

    /**
     *  测试用用户名查询用户
     * */
    @Test
    void testFindUserByUserName() {

        User user = userService.getOne(new QueryWrapper<User>().eq("username","aaa"));
//        roleService.getMap(new QueryWrapper<Role>().eq(""))
        System.out.println(user);
    }

    @Test
    void testHutool() {
//        User user = new User();
//        Map<Object, Object> map = MapUtil.builder()
//                .put("id", user.getId())
//                .put("username", user.getUsername())
//                .put("avatar", user.getAvatar())
//                .put("email", user.getEmail())
//                .map();
//        System.out.println(map);

        User user = new User();
        AccountProfile profile = new AccountProfile();
        user.setId((long) 1);
        user.setUsername("lmj");
        user.setAvatar("aa");
        user.setEmail("email");
        user.setPassword("111");

        BeanUtil.copyProperties(user, profile, "username", "avatar", "email");
        System.out.println(user);
        System.out.println(profile);
    }

    @Test
    void testAuthorization() {
        User user = userService.getById(2);
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
        System.out.println(MapUtil.builder()
                .put("id", user.getId())
                .put("username", user.getUsername())
                .put("avatar", user.getAvatar())
                .put("email", user.getEmail())
                .put("role", user.getRoles())
                .map());
    }
}
