package org.ketab.user;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.ketab.book.Book;
import org.ketab.book.BookManager;
import org.ketab.book.BookManagerBean;
import org.ketab.user.User;
import org.ketab.user.UserManager;


public class TestUserAndUserManagerBean{

	private Context ctx;
	private UserManager userMgrBean;
	
	@Before
	public void instantiate() throws NamingException{
		Hashtable<String, String> props = new Hashtable<String, String>();
		props.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.openejb.client.LocalInitialContextFactory");

		ctx = new InitialContext(props);
		userMgrBean = (UserManager)ctx.lookup("UserManagerBeanLocal");
	}

	
	@Test
	public void testGetUser() throws IOException, NoSuchAlgorithmException {
		User user = new User();
		user.setUserId("khalid");
		user.setPassword("khalid");
		userMgrBean.addUser(user);
		assertNotNull("Should get back a user.", userMgrBean.getUser(user.getUserId()));
	}
	
	@Test
	public void testIsUserValid() throws IOException, NoSuchAlgorithmException {
		User user = new User();
		user.setUserId("jamshed");
		user.setPassword("jamshed");
		userMgrBean.addUser(user);
				
		assertTrue("Should return true if the validation work. Hence the password encryption", userMgrBean.isUserValid(user));
	}

	@Test
	public void testRemoveUser() throws IOException, NamingException, NoSuchAlgorithmException{
		User user = new User();
		user.setUserId("rashed");
		user.setPassword("rashed");
		userMgrBean.addUser(user);

		userMgrBean.delUser(user.getUserId());

		assertNull("Shouldn't return a user.", userMgrBean.getUser(user.getUserId()));
	}

	@Test
	public void testUpdateBook() throws IOException, NamingException, NoSuchAlgorithmException{
		User user = new User();
		user.setUserId("abed");
		user.setPassword("abed");
		userMgrBean.addUser(user);
		
		User userToUpdate = userMgrBean.getUser(user.getUserId());

		userToUpdate.setPassword("abed2");

		userMgrBean.updateUser(userToUpdate);
		
		assertEquals("The passwords should match.", userMgrBean.getUser("abed").getPassword(), userToUpdate.getPassword());
	}

	@Test
	public void testListUsers() throws IOException, NamingException{
		userMgrBean = (UserManager)ctx.lookup("UserManagerBeanLocal");
		List<User> users = userMgrBean.listUsers("userId", "asc");

		for(int i = 0; i < users.size(); i++){
			assertTrue(users.get(i).getClass().isInstance(new User()));
		}

	}

}