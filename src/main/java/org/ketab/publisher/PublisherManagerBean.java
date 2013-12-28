package org.ketab.publisher;

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
public class PublisherManagerBean implements PublisherManager{

	@PersistenceContext(name="PublisherManagerBeanLocal", unitName="ketab", type=PersistenceContextType.TRANSACTION)
	private EntityManager em;
	
	
	@PostConstruct
	public void initialize(){
	}
	
	@PreDestroy
	public void cleanup(){
	}
	
	@Override
	public void addPub(Publisher pub) {
		em.persist(pub);
	}
	
	@Override
	public Publisher getPub(long pubId){
		return em.find(Publisher.class, pubId);
	};

	@Override
	public List listPubs(String sortBy, String sortType) {
		List books = em
				.createQuery("select pub from Publisher pub order by pub." + sortBy + " " + sortType)
				.getResultList();
		return books;
	}

	@Override
	public void delPub(long pubId) {
		em.createQuery("delete from Publisher where PUB_ID=:pubIdParam").setParameter("pubIdParam", pubId).executeUpdate();
	}

	@Override
	public void updatePub(Publisher pub) {
		em.merge(pub);
	}

}