package com.heyjianjun.shirovuedemo.shiro;

import com.heyjianjun.shirovuedemo.shiro.auth.AuthFilter;
import com.heyjianjun.shirovuedemo.shiro.realm.CustomerRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author : heyjianjun
 * @create 2021/8/24 16:49
 */
@Configuration
public class ShiroConfig {

    //1.创建shiroFilter  //负责拦截所有请求
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(WebSecurityManager webSecurityManager){

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //给filter设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(webSecurityManager);
        //auth过滤
        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("auth", new AuthFilter());
        shiroFilterFactoryBean.setFilters(filters);
        Map<String,String> map = new LinkedHashMap<String,String>();
        //配置系统公共资源
        map.put("/sys/login", "anon");
        map.put("/sys/register", "anon");
        //配置系统受限资源
        map.put("/**","auth");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

        return shiroFilterFactoryBean;
    }

    //2.创建安全管理器
    @Bean
    public WebSecurityManager webSecurityManager(CustomerRealm customerRealm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //给安全管理器设置
        defaultWebSecurityManager.setRealm(customerRealm);
        return defaultWebSecurityManager;
    }

    /**
     * Shiro 生命周期处理器，实现初始化和销毁回调
     */
    @Bean(name="lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * Shiro 过滤器代理配置
     */
    @Bean
    @DependsOn({ "lifecycleBeanPostProcessor" })
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator bean = new DefaultAdvisorAutoProxyCreator();
        bean.setProxyTargetClass(true);
        return bean;
    }

    /**
     * 启用Shrio授权注解拦截方式，AOP式方法级权限检查
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(WebSecurityManager webSecurityManager) {
        AuthorizationAttributeSourceAdvisor bean = new AuthorizationAttributeSourceAdvisor();
        bean.setSecurityManager(webSecurityManager);
        return bean;
    }

    @Bean
    public CustomerRealm customerRealm() {
        return new CustomerRealm();
    }
}
