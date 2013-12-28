package org.ketab.client;

import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;

import org.ketab.book.BookManagerBean;

@Named("ListBooks")
public class ListBooks {

	@EJB
	private BookManagerBean bmb;

	public List ListBooks(String sortBy, String sortType) {
		return bmb.listBooks(sortBy, sortType);
	}

}
