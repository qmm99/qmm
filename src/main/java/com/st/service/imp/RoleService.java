package com.st.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.st.bean.UserBean;
import com.st.dao.IRoleDao;
import com.st.service.IRoleService;
@Service("roleS")
public class RoleService implements IRoleService{
	@Autowired
	IRoleDao roleD;
	@Override
	public List allrole( Map params) {
		// TODO Auto-generated method stub
		return roleD.allrole(params);
	}
	@Override
	public void addrole(Map params) {
		// TODO Auto-generated method stub
		String uuid=UUID.randomUUID().toString();
		params.put("role_id", uuid);
		roleD.addrole(params);
	}
	@Override
	public void delrole(String[] role_ids) {
		// TODO Auto-generated method stub
		System.out.println(role_ids);
		for(int i=0;i<role_ids.length;i++) {
			Map params=new HashMap();
			params.put("role_id", role_ids[i]);
			roleD.delrole(params);
		}
	}
	@Override
	public void uprole(Map params) {
		// TODO Auto-generated method stub
		roleD.uprole(params);
	}
	@Override
	public List role_treeJson() {
		// TODO Auto-generated method stub
		return  roleD.role_treeJson();
	}
	//保存角色和权限的关联
	@Override
	public void addrole_perm(String role_id,String perms) {
		// TODO Auto-generated method stub
		//不推荐的一种做法
		//1.将perms转为集合
		List list=toList(perms);
		Map map=new HashMap();
		map.put("perms", list);
		map.put("role_id", role_id);
		roleD.addrole_perm(map);
	}
	private List toList(String perms) {
		List list=new ArrayList();
		String[] perm_arr=perms.split(",");
		for(String str:perm_arr) {
			if(!str.trim().equals("")) {
				list.add(str);
			}
		}
		return list;
	}
	@Override
	public List roleid_perm(Map params) {
		// TODO Auto-generated method stub
		return roleD.roleid_perm(params);
	}
	@Override
	public UserBean getUser(String username) {
		// TODO Auto-generated method stub
		return roleD.getUser(username);
	}
	@Override
	public List getPermByUsername(String username) {
		// TODO Auto-generated method stub
		return roleD.getPermByUsername(username);
	}

}
