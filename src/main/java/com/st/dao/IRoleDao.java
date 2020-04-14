package com.st.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.st.bean.UserBean;

@Repository("roleD")
public interface IRoleDao {
	List allrole(Map params);
	void addrole(Map params);
	void delrole(Map params);
	void uprole(Map params);
	List role_treeJson();
	void addrole_perm(Map params);
	List id_params(Map params);
	List roleid_perm(Map params);
	UserBean getUser(String username);
	List getPermByUsername(String username);
}
