package org.ketab.review;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.ketab.utils.Utils;

import sun.misc.BASE64Encoder;


@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ReviewManagerBean implements ReviewManager{

	@PersistenceContext(name="BookManagerBeanLocal", unitName="ketab", type=PersistenceContextType.TRANSACTION)
	private EntityManager em;
	
	private Utils utils;
	
	
	@PostConstruct
	public void initialize(){
	}
	
	@PreDestroy
	public void cleanup(){
	}
	
	@Override
	public void addRev(Review rev) {
		em.persist(rev);
	}
	
	@Override
	public Review getRev(long revId){
		return em.find(Review.class, revId);
	}

	@Override
	public List listRevs(String sortBy, String sortType) {
		List revs = em
				.createQuery("select rev from Review rev order by rev" + sortBy + " " + sortType)
				.getResultList();
		return revs;
	}

	@Override
	public void delRev(long revId) {
		em.createQuery("delete from Review where REV_ID=:revIdParam").setParameter("revIdParam", revId).executeUpdate();
	}

	@Override
	public void updateRev(Review rev) {
		em.merge(rev);
	}

}