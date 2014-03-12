package org.ketab.controller;

import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import org.apache.commons.io.IOUtils;
import org.ketab.book.Book;
import org.ketab.book.BookManager;
import org.ketab.book.BookManagerBean;

@Named("books")
public class BooksBean {

	@EJB
	private BookManager bm;
	Book newBook;

	public List ListBooks(String sortBy, String sortType) {
		return bm.listBooks(sortBy, sortType);
	}
	
	public boolean addBook(){
		Book newBook = new Book();
		newBook.setUploadDate(new Date());
		//newBook.setPagesCount(pagesCount); //TODO: Extract the number of pages from pdf.
		//newBook.setAuthors(authors); //TODO: Get Authors using the given ids.
		//newBook.setUploader(uploader); //TODO: Get the userid from user's session.
		//newBook.setPublisher(publisher); //TODO: Get publisher object using the given id.
		try{
			bm.addBook(newBook);
		}
		catch(Exception e){
			System.out.println(e.getStackTrace());
			return false;
		}
		return true;
	}
	public boolean removeBook(long bookId){
		bm.delBook(bookId);
		return false;	//TODO: Implement this in BookManagerBean
	}
	public boolean updateBook(Book book){
		bm.updateBook(book);
		return false;	//TODO: Implement this in BookManagerBean
	}
	
	public Book getBook(long bookId){
		return bm.getBook(bookId);
	}

}
