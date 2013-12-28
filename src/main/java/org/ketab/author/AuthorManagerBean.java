package org.ketab.author;

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


@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class AuthorManagerBean implements AuthorManager{

	@PersistenceContext(name="AuthorManagerBeanLocal", unitName="ketab", type=PersistenceContextType.TRANSACTION)
	private EntityManager em;
	
	
	@PostConstruct
	public void initialize(){
	}
	
	@PreDestroy
	public void cleanup(){
	}
	
	@Override
	public void addAuthr(Author author) {
		em.persist(author);
	}
	
	@Override
	public Author getAuthr(long authrId){
		return em.find(Author.class, authrId);
	}

	@Override
	public List listAuthrs(String sortBy, String sortType) {
		List auths = em
				.createQuery("select authr from Author authr order by authr." + sortBy + " " + sortType)
				.getResultList();
		return auths;
	}

	@Override
	public void delAuthr(long authrId) {
		em.createQuery("delete from Author where AUTHR_ID=:authrIdParam").setParameter("authrIdParam", authrId).executeUpdate();
	}

	@Override
	public void updateAuthr(Author author) {
		em.merge(author);
	}

}