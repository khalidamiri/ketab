package org.ketab.author;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.io.IOUtils;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ketab.author.Author;
import org.ketab.author.AuthorManager;
import org.ketab.author.AuthorManagerBean;
import org.ketab.user.User;

@RunWith(Arquillian.class)
public class TestAuthorAndAuthorManagerBean{

//	private Context ctx;
	
	//TODO: Make this test case run successfully. Study about CDI stuff.
	
	@Inject
	@Named
	private AuthorManagerBean authrMb;

	@EJB
	private AuthorManager authrMgr;
	
	@Deployment
	public static JavaArchive createDeployment(){
		JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
				.addClass(AuthorManager.class)
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
		return jar;
	}
	
	@Before
	public void instantiate() throws NamingException{
		Hashtable<String, String> props = new Hashtable<String, String>();
		props.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.openejb.client.LocalInitialContextFactory");
//		props.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");

//		ctx = new InitialContext(props);
//		authrMb = (AuthorManager)ctx.lookup("AuthorManagerBeanLocal");
	}

	@Test
	public void testGetAuthr() throws IOException {
		Author author = new Author();
		author.setAuthrName("Mojawer Ahmad Ziyar");
		authrMb.addAuthr(author);
		assertNotNull("Should get back an author.", authrMb.getAuthr(author.getAuthrId()));
	}
		
	@Test
	public void testDeleteAuthor() throws IOException, NamingException{
		Author author = new Author();
		author.setAuthrName("Author to be removed");
//		authrMb = (AuthorManager)ctx.lookup("AuthorManagerBeanLocal");
		authrMb.addAuthr(author);

		authrMb.delAuthr(author.getAuthrId());

		assertNull("Shouldn't return a author with title 'Author to be removed', if 'remove' works.", authrMb.getAuthr(author.getAuthrId()));
	}

	@Test
	public void testUpdateAuthor() throws IOException, NamingException{
		Author author = new Author();
		author.setAuthrName("Author to be Updated");
//		authrMb = (AuthorManager)ctx.lookup("AuthorManagerBeanLocal");
		authrMb.addAuthr(author);
		
		Author authorToUpdate = authrMb.getAuthr(author.getAuthrId());
		authorToUpdate.setAuthrName("Author just updated");

		authrMb.updateAuthr(authorToUpdate);
		
		assertEquals("Shouldn't return a author with title 'Author to be removed', if 'remove' works.", authrMb.getAuthr(author.getAuthrId()).getAuthrName(), authorToUpdate.getAuthrName());
	}

	@Test
	public void testListAuthors() throws IOException, NamingException{
//		authrMb = (AuthorManager)ctx.lookup("AuthorManagerBeanLocal");
		List<Author> authors = authrMb.listAuthrs("authrId", "asc");

		for(int i = 0; i < authors.size(); i++){
			assertTrue(authors.get(i).getClass().isInstance(new Author()));
		}

	}

}