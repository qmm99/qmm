package com.st.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.st.bean.JSON;
import com.st.service.IRoleService;

@Controller
@RequestMapping("/sys")
public class Action2 {
	@Autowired
	IRoleService roleS;

	// 进入角色管理页面
	@RequestMapping("/role")
	@RequiresPermissions("user_system") //必须有权限才可访问
	public String role() {
		System.out.println("进入角色管理");
		return "../role.jsp";//向上跳一级  相对路径是sys/role.jsp
	}

	// 查询role信息
	@RequestMapping("/allrole")
	@ResponseBody
	public JSON allrole(@RequestParam Map params) {
		List list = roleS.allrole(params);
		JSON json = new JSON(list);
		return json;
	}

	// 新增role
	@RequestMapping("/addrole")
	@ResponseBody
	public void addrole(@RequestParam Map params) {
		roleS.addrole(params);
	}

	// 删除role
	@RequestMapping("/delrole")
	@ResponseBody
	public void delrole(@RequestParam(value = "role_ids[]") String[] role_ids) {
		roleS.delrole(role_ids);
	}

	// 修改role
	@RequestMapping("/uprole")
	@ResponseBody
	public void uprole(@RequestParam Map params) {
		roleS.uprole(params);
	}

	// 进入权限页面
	@RequestMapping("/quanxian/{role_id}")
	public String quanxian(@PathVariable String role_id, HttpServletRequest request) {
		request.setAttribute("role_id", role_id);
		return "../quanxian.jsp";
	}

	// 查询权限信息
	@RequestMapping("/role_treeJson")
	@ResponseBody
	public List role_treeJson() {
		List list = roleS.role_treeJson();
		return list;
	}

	// 给角色赋予权限
	@RequestMapping("/role_perm/{role_id}/{perms}")
	@ResponseBody
	public void role_perm(@PathVariable String role_id, @PathVariable String perms) {
		System.out.println("进来了");
		System.out.println(role_id + "role_id");
		System.out.println(perms + "perms");
		roleS.addrole_perm(role_id, perms);
	}

	// 查询该角色的所有权限
	@RequestMapping("/roleid_perm")
	@ResponseBody
	public JSON roleid_perm(@RequestParam Map params) {
		List list = roleS.roleid_perm(params);
		JSON json = new JSON(list);
		return json;
	}
}
