package com.lmj.vueblog.config.shiro;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro配置类
 * */
@Configuration
public class ShiroConfig {

//    @Autowired
//    JwtFilter jwtFilter;

    /**
     * 1.创建过滤器工厂
     * */
    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager,
                                                         ShiroFilterChainDefinition shiroFilterChainDefinition) {
        System.out.println("执行了=>shiroFilterFactoryBean");

        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilter.setSecurityManager(securityManager);

        Map<String, Filter> filters = new HashMap<>();
        // 所有的路由都需要经过JwtFilter这个过滤器，然后判断请求头中是否含有jwt的信息，有就登录，没有就跳过。
        // 跳过之后，有Controller中的shiro注解进行再次拦截，
        filters.put("jwt", new JwtFilter());
//        filters.put("jwt", jwtFilter);
        shiroFilter.setFilters(filters);

        Map<String, String> filterMap = shiroFilterChainDefinition.getFilterChainMap();
        shiroFilter.setFilterChainDefinitionMap(filterMap);

        return shiroFilter;
    }
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();

        Map<String, String> filterMap = new LinkedHashMap<>();

        filterMap.put("/**", "jwt");
//        filterMap.put("/**", "authc");
//        filterMap.put("/logout", "authc");
//        filterMap.put("/blog/edit", "authc");
//        filterMap.put("/index", "authc");


        chainDefinition.addPathDefinitions(filterMap);

        return chainDefinition;
    }

    /**
     * 2.securityManager,注入自定义Realm,sessionManager,redisCacheManager
     * */
    @Bean
    public DefaultWebSecurityManager securityManager(Realm realm,
                                                     SessionManager sessionManager,
                                                     RedisCacheManager redisCacheManager) {
        System.out.println("执行了=>securityManager");
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(realm);

        // inject sessionManager
        securityManager.setSessionManager(sessionManager);

        // inject redisCacheManager
        securityManager.setCacheManager(redisCacheManager);
        return securityManager;
    }
    @Bean
    public SessionManager sessionManager(RedisSessionDAO redisSessionDAO) {
        System.out.println("执行了=>sessionManager");
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();

        // inject redisSessionDAO
        sessionManager.setSessionDAO(redisSessionDAO);
        return sessionManager;
    }

    //3.创建自定义realm
    @Bean
    public Realm getRealm(){
        AccountRealm customerRealm = new AccountRealm();
//        // 设置密码匹配器
//        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
//        // 设置加密方式
//        credentialsMatcher.setHashAlgorithmName(ShiroConstant.HASH_ALGORITHM_NAME.MD5);
//        // 设置散列次数
//        credentialsMatcher.setHashIterations(ShiroConstant.HASH_ITERATORS);
//        customerRealm.setCredentialsMatcher(credentialsMatcher);

//        // 设置缓存管理器
//        customerRealm.setCacheManager(new EhCacheManager());
//        customerRealm.setCacheManager(new com.markerhub.config.shiro.cache.RedisCacheManager());
//        // 开启全局缓存
//        customerRealm.setCachingEnabled(true);
//        // 开启认证缓存并指定缓存名称
//        customerRealm.setAuthenticationCachingEnabled(true);
//        customerRealm.setAuthenticationCacheName("authenticationCache");
//        // 开启授权缓存并指定缓存名称
//        customerRealm.setAuthorizationCachingEnabled(true);
//        customerRealm.setAuthorizationCacheName("authorizationCache");
        return customerRealm;
    }
}
