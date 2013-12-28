package org.ketab.author;

import java.util.List;


public interface AuthorManager {

	public void addAuthr(Author author);
	public Author getAuthr(long authrId);
	public void delAuthr(long authrId);
	public void updateAuthr(Author author);
	public List listAuthrs(String sortBy, String sortType);
}
