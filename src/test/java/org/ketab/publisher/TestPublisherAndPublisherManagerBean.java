package org.ketab.publisher;

import static org.junit.Assert.*;
import static org.hamcrest.core.IsEqual.*;

import java.util.Arrays;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ketab.publisher.Publisher;
import org.ketab.publisher.PublisherManager;
import org.ketab.review.Review;
import org.ketab.review.ReviewManager;
import org.ketab.user.Role;
import org.ketab.user.RoleManager;

public class TestPublisherAndPublisherManagerBean {

	InitialContext ctx;
	PublisherManager pubMb;
	
	@Before
	public void initialize() throws NamingException{
		Hashtable<String, String> props = new Hashtable<String, String>();
		props.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.openejb.client.LocalInitialContextFactory");
		
		ctx = new InitialContext(props);
		pubMb = (PublisherManager)ctx.lookup("PublisherManagerBeanLocal");
	}

	@Test
	public void testGetPub() {
		Publisher pub = new Publisher();
		pub.setPubName("A Publisher");
		
		pubMb.addPub(pub);
		assertNotNull("Should return a role object.", pubMb.getPub(pub.getPubId()));
	}
	
	@Test
	public void testDelPub(){
		Publisher pub = new Publisher();
		pub.setPubName("A Publisher");
		
		pubMb.addPub(pub);
		
		pubMb.delPub(pub.getPubId());
		assertNull("Shouldn't return any thing.", pubMb.getPub(pub.getPubId()));
	}
	
	@Test
	public void testUpdatePub(){
		Publisher pub = new Publisher();
		pub.setPubName("A Publisher");
		
		pubMb.addPub(pub);
		
		Publisher pubToUpdate = pubMb.getPub(pub.getPubId());
		pubToUpdate.setPubName("Updated Publisher");
		pubMb.updatePub(pubToUpdate);
		assertThat("The role names shouldn't match.", pubMb.getPub(pub.getPubId()).getPubName(), equalTo("Updated Publisher"));
	}

	@Test
	public void testListPubs(){
		Publisher[] pubs = new Publisher[10];
		Publisher[] pubsReturned = new Publisher[10];

		for(int i = 0; i < pubs.length; i++){
			pubs[i] = new Publisher();
			pubs[i].setPubName("Title " + i);
			pubMb.addPub(pubs[i]);
		}
		
		for(int i = 0; i < pubs.length; i++){
			pubsReturned[i] = pubMb.getPub(pubs[i].getPubId());
			assertTrue(pubsReturned[i].getClass().isInstance(new Publisher()));
		}

	}
}
