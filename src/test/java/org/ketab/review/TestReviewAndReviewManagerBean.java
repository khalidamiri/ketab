package org.ketab.review;

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
import org.ketab.review.Review;
import org.ketab.review.ReviewManager;
import org.ketab.user.Role;
import org.ketab.user.RoleManager;

public class TestReviewAndReviewManagerBean {

	InitialContext ctx;
	ReviewManager revMb;
	
	@Before
	public void initialize() throws NamingException{
		Hashtable<String, String> props = new Hashtable<String, String>();
		props.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.openejb.client.LocalInitialContextFactory");
		
		ctx = new InitialContext(props);
		revMb = (ReviewManager)ctx.lookup("ReviewManagerBeanLocal");
	}

	@Test
	public void testGetReview() {
		Review rev = new Review();
		rev.setRevTitle("A nice review.");
		
		revMb.addRev(rev);
		assertNotNull("Should return a role object.", revMb.getRev(rev.getRevId()));
	}
	
	@Test
	public void testRemoveRev(){
		Review rev = new Review();
		rev.setRevTitle("A nice review.");
		revMb.addRev(rev);
		
		revMb.delRev(rev.getRevId());
		assertNull("Shouldn't return any thing.", revMb.getRev(rev.getRevId()));
	}
	
	@Test
	public void testUpdateRev(){
		Review rev = new Review();
		rev.setRevTitle("A nice review.");
		revMb.addRev(rev);
		
		Review revToUpdate = revMb.getRev(rev.getRevId());
		revToUpdate.setRevTitle("A not so nice review");
		revMb.updateRev(revToUpdate);
		assertThat("The role names shouldn't match.", revMb.getRev(rev.getRevId()).getRevTitle(), equalTo("A not so nice review"));
	}

	@Test
	public void testListRevs(){
		Review[] revs = new Review[10];
		Review[] revsReturned = new Review[10];

		for(int i = 0; i < revs.length; i++){
			revs[i] = new Review();
			revs[i].setRevTitle("Title " + i);
			revMb.addRev(revs[i]);
		}
		
		for(int i = 0; i < revs.length; i++){
			revsReturned[i] = revMb.getRev(revs[i].getRevId());
			assertTrue(revsReturned[i].getClass().isInstance(new Review()));
		}

	}
}
