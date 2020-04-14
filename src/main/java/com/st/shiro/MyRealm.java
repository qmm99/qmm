package com.st.shiro;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.st.bean.PermBean;
import com.st.bean.UserBean;
import com.st.service.IRoleService;
@Component
//把javaBean纳入spring容器中
public class MyRealm extends AuthorizingRealm{
	@Autowired
	IRoleService roleS;
	/**
	 * 该方法一定是登陆成功之后才会运行的
	 * 每当需要权限认证时，会执行的方法
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		System.out.println("---------------------获取权限");
		String username=(String) SecurityUtils.getSubject().getPrincipal();//获取当前用户名
		List list=roleS.getPermByUsername(username);//PermBean对象的一个集合获取该用户的所有权限
		SimpleAuthorizationInfo sai=new SimpleAuthorizationInfo();
		//需要一个字符串的集合
		List<String> Str_perm_list=getPermList(list);
		System.out.println(Str_perm_list+"权限字符串的集合");
		sai.addStringPermissions(Str_perm_list);
		return sai;
	}
	private List<String> getPermList(List list){
		List<PermBean> PBlist=(List<PermBean>)list;
		List<String> Str_perm_list=new ArrayList<String>();
		for(PermBean pb:PBlist) {
			Str_perm_list.add(pb.getPerm_code());
		}
		return Str_perm_list;
	}
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// TODO Auto-generated method stub  存放用户名 密码的数据
		//获得用户名和密码
		String username=token.getPrincipal().toString();//用户名
		String password=new String((char[])token.getCredentials());
		//MD5加密
		SimpleHash sh=new SimpleHash("md5", password);
		System.out.println(sh.toString()+"md5加密");
		sh=new SimpleHash("md5",password,username);
		System.out.println(sh.toString()+"加了md5+颜值"); //颜值不可变
		sh=new SimpleHash("md5",password,username,12);
		System.out.println(sh.toString()+"加了md5+颜值+次数");
		//数据库验证是否正确
		System.out.println("-----没有做验证");
		UserBean user=roleS.getUser(username);
		if(!sh.toString().equals(user.getPassword())) {
			System.out.println("错误");
			throw new CredentialsException("密码不正确");
		}
		return new SimpleAuthenticationInfo(username,password,getName());
	}
	
}
