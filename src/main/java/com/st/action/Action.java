package com.st.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.st.service.IRoleService;

@Controller
public class Action {
	@Autowired
	IRoleService roleS;
	
	@RequestMapping("/login")
	public String login() {
		return "login.jsp";
	}
	@RequestMapping("/tologin")
	public String tologin() {
		return "tologin.jsp";
	}
	@RequestMapping("/login_system")
	public String login_system(String username ,String password,HttpServletRequest request) {
		//生成安全令牌
		UsernamePasswordToken token=new UsernamePasswordToken(username,password);//使用用户名和密码生成安全令牌
		Subject sub=SecurityUtils.getSubject();//获取当前访问用户的对象Subject  根据会话id获得
		System.out.println(sub+"当前访问用户的对象");
		System.out.println("生成了安全令牌");
		try {
			sub.login(token);//执行开始登陆--执行realm
			System.out.println("出来了");
		}catch(CredentialsException ce) {
			request.setAttribute("msg", ce.getMessage());
			return "login.jsp";
		}
		return "houtai.jsp";
	}
}
