package org.ketab.user;

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
public class UserManagerBean implements UserManager{

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
	public void addUser(User user) {
		em.persist(user);
	}
	
	@Override
	public User getUser(String userId){
		return em.find(User.class, userId);
	}

	@Override
	public List listUsers(String sortBy, String sortType) {
		List books = em
				.createQuery("select u from User u order by u." + sortBy + " " + sortType)
				.getResultList();
		return books;
	}

	@Override
	public void delUser(String userId) {
		em.createQuery("delete from User where USER_ID=:userIdParam").setParameter("userIdParam", userId).executeUpdate();
	}

	@Override
	public void updateUser(User user) {
		em.merge(user);
	}

	@Override
	public boolean isUserValid(User user) {
		utils = new Utils();
		User userFound = em.find(User.class, user.getUserId());
		if(user.getUserId().equals(userFound.getUserId()) && user.getPassword().equals(userFound.getPassword()))
			return true;
		else
			return false;
	}

}