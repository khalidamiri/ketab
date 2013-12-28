package org.ketab.category;

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
public class CategoryManagerBean implements CategoryManager{

	@PersistenceContext(name="PublisherManagerBeanLocal", unitName="ketab", type=PersistenceContextType.TRANSACTION)
	private EntityManager em;
	
	
	@PostConstruct
	public void initialize(){
	}
	
	@PreDestroy
	public void cleanup(){
	}
	
	@Override
	public void addCat(Category cat) {
		em.persist(cat);
	}
	
	@Override
	public Category getCat(long catId){
		return em.find(Category.class, catId);
	};

	@Override
	public List listCats(String sortBy, String sortType) {
		List cats = em
				.createQuery("select cat from Category cat order by cat." + sortBy + " " + sortType)
				.getResultList();
		return cats;
	}

	@Override
	public void delCat(long catId) {
		em.createQuery("delete from Category where CAT_ID=:catIdParam").setParameter("catIdParam", catId).executeUpdate();
	}

	@Override
	public void updateCat(Category cat) {
		em.merge(cat);
	}

}