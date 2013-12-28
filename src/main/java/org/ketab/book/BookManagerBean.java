package org.ketab.book;

import java.io.IOException;
import java.io.InputStream;
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
public class BookManagerBean implements BookManager{

	@PersistenceContext(name="BookManagerBeanLocal", unitName="ketab", type=PersistenceContextType.TRANSACTION)
	private EntityManager em;
		
	
	@PostConstruct
	public void initialize(){
	}
	
	@PreDestroy
	public void cleanup(){
	}
	
	@Override
	public void addBook(Book book) {
		em.persist(book);
	}
	
	@Override
	public Book getBook(long bookId){
		return em.find(Book.class, bookId);
	}

	@Override
	public List listBooks(String sortBy, String sortType) {
		List books = em
				.createQuery("select b from Book b order by b." + sortBy + " " + sortType)
				.getResultList();
		return books;
	}

	@Override
	public void delBook(long bookId) {
		em.createQuery("delete from Book where BOOK_ID=:bookIdParam").setParameter("bookIdParam", bookId).executeUpdate();
	}

	@Override
	public void updateBook(Book book) {
		em.merge(book);
	}
	
}