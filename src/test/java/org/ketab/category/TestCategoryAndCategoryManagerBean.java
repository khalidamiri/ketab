package org.ketab.category;

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
import org.ketab.category.Category;
import org.ketab.category.CategoryManager;
import org.ketab.review.Review;
import org.ketab.review.ReviewManager;
import org.ketab.user.Role;
import org.ketab.user.RoleManager;

@RunWith(Arquillian.class)
public class TestCategoryAndCategoryManagerBean {

//	InitialContext ctx;
	@EJB
	CategoryManager catMb;
	
	@Deployment
	public static JavaArchive createDeployment(){
		JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
				.addClasses(CategoryManager.class, CategoryManagerBean.class)
				.addAsResource("META-INF/persistence.xml");
		return jar;
	}

/*	@Before
	public void initialize() throws NamingException{
		Hashtable<String, String> props = new Hashtable<String, String>();
		props.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.openejb.client.LocalInitialContextFactory");
		
		ctx = new InitialContext(props);
		catMb = (CategoryManager)ctx.lookup("CategoryManagerBeanLocal");
	}
*/

	@Test
	public void testGetCat() {
		Category cat = new Category();
		cat.setCatName("A Category");
		
		catMb.addCat(cat);
		assertNotNull("Should return a role object.", catMb.getCat(cat.getCatId()));
	}
	
	@Test
	public void testDelCat(){
		Category cat = new Category();
		cat.setCatName("A Category");
		
		catMb.addCat(cat);
		
		catMb.delCat(cat.getCatId());
		assertNull("Shouldn't return any thing.", catMb.getCat(cat.getCatId()));
	}
	
	@Test
	public void testUpdateCat(){
		Category cat = new Category();
		cat.setCatName("A Category");
		
		catMb.addCat(cat);
		
		Category pubToUpdate = catMb.getCat(cat.getCatId());
		pubToUpdate.setCatName("Updated Category");
		catMb.updateCat(pubToUpdate);
		assertThat("The role names shouldn't match.", catMb.getCat(cat.getCatId()).getCatName(), equalTo("Updated Category"));
	}

	@Test
	public void testListCats(){
		Category[] cats = new Category[10];
		Category[] catsReturned = new Category[10];

		for(int i = 0; i < cats.length; i++){
			cats[i] = new Category();
			cats[i].setCatName("Title " + i);
			catMb.addCat(cats[i]);
		}
		
		for(int i = 0; i < cats.length; i++){
			catsReturned[i] = catMb.getCat(cats[i].getCatId());
			assertTrue(catsReturned[i].getClass().isInstance(new Category()));
		}

	}
}
