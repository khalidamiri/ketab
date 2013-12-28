package org.ketab.user;

import java.util.List;

public interface RoleManager {

	public void addRole(Role role);
	public Role getRole(long roleId);
	public void delRole(Long roleId);
	public void updateRole(Role role);
	public List listRoles();
}
