package com.st.service;

import java.util.List;
import java.util.Map;

import com.st.bean.UserBean;

public interface IRoleService {
	List allrole( Map params);
	void addrole(Map params);
	void delrole(String[] role_ids);
	void uprole(Map params);
	List role_treeJson();
	void addrole_perm(String role_id,String perms);
	List roleid_perm(Map params);
	UserBean getUser(String username);
	List getPermByUsername(String username);
}
