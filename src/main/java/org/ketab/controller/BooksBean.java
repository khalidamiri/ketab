package org.ketab.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import org.ketab.book.Book;
import org.ketab.book.BookManagerBean;

@Named("Books")
public class BooksBean {

	@EJB
	private BookManagerBean bmb;

	public List ListBooks(String sortBy, String sortType) {
		return bmb.listBooks(sortBy, sortType);
	}
	
	@Produces
	@Named
	public boolean addBook(Book book){
		Book bookToAdd = new Book();
//		book
		return false;
	}
	public boolean removeBook(long bookId){return false;}
	public boolean updateBook(Book book){return false;}
	public boolean viewBook(long bookId){return false;}

}
