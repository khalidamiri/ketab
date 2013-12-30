package org.ketab.user;

import static org.junit.Assert.*;
import static org.hamcrest.core.IsEqual.*;

import java.util.Arrays;
import java.util.Hashtable;

import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ketab.book.BookManager;
import org.ketab.book.BookManagerBean;
import org.ketab.user.Role;
import org.ketab.user.RoleManager;

@RunWith(Arquillian.class)
public class TestRoleAndRoleManagerBean {

//	InitialContext ctx;
	@EJB
	RoleManager rmb;
	
	@Deployment
	public static JavaArchive createDeployment(){
		JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
				.addClasses(RoleManager.class, RoleManagerBean.class)
				.addAsResource("META-INF/persistence.xml");
		return jar;
	}

/*	@Before
	public void initialize() throws NamingException{
		Hashtable<String, String> props = new Hashtable<String, String>();
		props.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.openejb.client.LocalInitialContextFactory");
		
		ctx = new InitialContext(props);
		rmb = (RoleManager)ctx.lookup("RoleManagerBeanLocal");
	}
*/
	
	@Test
	public void testGetRole() {
		Role role = new Role();
		role.setRoleName("ReviewMonitor");
		
		rmb.addRole(role);
		assertNotNull("Should return a role object.", rmb.getRole(role.getRoleId()));
	}
	
	@Test
	public void testRemoveRole(){
		Role role = new Role();
		role.setRoleName("Administrator");
		rmb.addRole(role);
		
		rmb.delRole(role.getRoleId());
		assertNull("Shouldn't return any thing.", rmb.getRole(role.getRoleId()));
	}
	
	@Test
	public void testUpdateRole(){
		Role role = new Role();
		role.setRoleName("Manager");
		rmb.addRole(role);
		
		Role roleToUpdate = rmb.getRole(role.getRoleId());
		roleToUpdate.setRoleName("Admin");
		rmb.updateRole(roleToUpdate);
		assertThat("The role names shouldn't match.", rmb.getRole(role.getRoleId()).getRoleName(), equalTo("Admin"));
	}

	@Test
	public void testListRoles(){
		Role[] roles = new Role[10];
		Role[] rolesReturned = new Role[10];

		for(int i = 0; i < roles.length; i++){
			roles[i] = new Role();
			roles[i].setRoleName("Role " + i);
			rmb.addRole(roles[i]);
		}
		
		for(int i = 0; i < roles.length; i++){
			rolesReturned[i] = rmb.getRole(roles[i].getRoleId());
			assertTrue(rolesReturned[i].getClass().isInstance(new Role()));
		}

	}
}
