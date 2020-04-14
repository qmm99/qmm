package com.st.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class Config {
	/**
	 * 创建安全管理器
	 * securityManager 安全管理器
	 * @return
	 */
	@Bean
	public org.apache.shiro.mgt.SecurityManager securityManager(AuthorizingRealm realm) {
       //SecurityManager该接口的实现类  
		DefaultWebSecurityManager defaultSecurityManager = new DefaultWebSecurityManager();
        defaultSecurityManager.setRealm(realm);
        return defaultSecurityManager;
    }
	/**
	 * 因为创建的使web项目所以要加shiro过滤器
	 */
	@Bean(name = "shiroFilter")  //给Bean指定ID
    public ShiroFilterFactoryBean shiroFilter(org.apache.shiro.mgt.SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager); //给这个工厂对象设置一个安全管理器
        shiroFilterFactoryBean.setLoginUrl("/tologin");	//设置登录页面的路径--会判到是否登录，若没有则会跳转到这个路径上
        shiroFilterFactoryBean.setUnauthorizedUrl("notRole");//权限不足的路径
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
       //过滤登录与否，与能否访问的路径
//        filterChainDefinitionMap.put("/webjars/**", "anon"); //不需要权限的即可访问的
//        filterChainDefinitionMap.put("/houtai", "anon");
        filterChainDefinitionMap.put("/", "anon"); //一级路径不需要拦截
        filterChainDefinitionMap.put("/front/**", "anon");
        filterChainDefinitionMap.put("/api/**", "anon");

        filterChainDefinitionMap.put("/sys/**", "authc");//必须经过授权才可访问的路径
        filterChainDefinitionMap.put("/user/**", "authc");// /** 代表多层路径
        //主要这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截 剩余的都需要认证
        //如何判断是否登录
        //  filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }
	//添加了一个代理工具
	@Bean
	public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		advisorAutoProxyCreator.setProxyTargetClass(true);
		return advisorAutoProxyCreator;
	}
	//开启注解支持
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
			org.apache.shiro.mgt.SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

}
