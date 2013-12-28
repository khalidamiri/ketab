package org.ketab.user;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;

@Local
public interface UserManager {

	public void addUser(User user);
	public void delUser(String userId);
	public void updateUser(User user);
	public List listUsers(String sortBy, String sortType);
	public boolean isUserValid(User user);
	public User getUser(String userId);
}
