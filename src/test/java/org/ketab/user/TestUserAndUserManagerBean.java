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

import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.io.IOUtils;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ketab.book.Book;
import org.ketab.book.BookManager;
import org.ketab.book.BookManagerBean;
import org.ketab.user.User;
import org.ketab.user.UserManager;

@RunWith(Arquillian.class)
public class TestUserAndUserManagerBean{

	
	//TODO: Make this test case run. Fix the issues.
	

//	private Context ctx;
	@EJB
	private UserManager userMgrBean;
	
	@Deployment
	public static JavaArchive createDeployment(){
		JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
				.addClasses(UserManager.class, UserManagerBean.class)
				.addAsResource("META-INF/persistence.xml");
		return jar;
	}

/*	@Before
	public void instantiate() throws NamingException{
		Hashtable<String, String> props = new Hashtable<String, String>();
		props.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.openejb.client.LocalInitialContextFactory");

		ctx = new InitialContext(props);
		userMgrBean = (UserManager)ctx.lookup("UserManagerBeanLocal");
	}
*/
	
	@Test
	public void testGetUser() throws IOException, NoSuchAlgorithmException {
		User user = new User();
		String userId = (Math.random() * 1000) + " ";
		user.setUserId(userId);
		while(userMgrBean.getUser(user.getUserId()) instanceof User){		// To avoid storing duplicate keys
			userId = (Math.random() * 1000) + " ";
		}
		user.setPassword("khalid");
		userMgrBean.addUser(user);
		assertNotNull("Should get back a user.", userMgrBean.getUser(user.getUserId()));
	}
	
	@Test
	public void testIsUserValid() throws IOException, NoSuchAlgorithmException {
		User user = new User();
		String userId = (Math.random() * 1000) + " ";
		user.setUserId(userId);
		while(userMgrBean.getUser(user.getUserId()) instanceof User){		// To avoid storing duplicate keys
			userId = (Math.random() * 1000) + " ";
		}
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
	public void testUpdateUser() throws IOException, NamingException, NoSuchAlgorithmException{
		User user = new User();
		String userId = (Math.random() * 1000) + " ";
		user.setUserId(userId);
		while(userMgrBean.getUser(user.getUserId()) instanceof User){		// To avoid storing duplicate keys
			userId = (Math.random() * 1000) + " ";
		}
		user.setPassword("abed");
		userMgrBean.addUser(user);
		
		User userToUpdate = userMgrBean.getUser(user.getUserId());

		userToUpdate.setPassword("abed2");

		userMgrBean.updateUser(userToUpdate);
		
		assertEquals("The passwords should match.", userMgrBean.getUser(userId).getPassword(), userToUpdate.getPassword());
	}

	@Test
	public void testListUsers() throws IOException, NamingException{
//		userMgrBean = (UserManager)ctx.lookup("UserManagerBeanLocal");
		List<User> users = userMgrBean.listUsers("userId", "asc");

		for(int i = 0; i < users.size(); i++){
			assertTrue(users.get(i).getClass().isInstance(new User()));
		}

	}

}