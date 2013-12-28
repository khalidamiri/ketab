package org.ketab.book;

import java.util.List;
import javax.ejb.Local;

@Local
public interface BookManager{

	public void addBook(Book book);
	public void updateBook(Book book);
	public List listBooks(String sortBy, String sortType);
	public Book getBook(long bookId);
	public void delBook(long bookId);
}
