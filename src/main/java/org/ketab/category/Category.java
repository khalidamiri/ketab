package org.ketab.category;

import javax.persistence.*;

import org.ketab.book.Book;

import java.util.List;

@Entity
@Table(name = "CATEGORY")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CAT_ID")
	private long catId;
	
	@Column(name = "CAT_NAME")
	private String catName;
	
	@Column(name = "CAT_DESC")
	private String catDesc;

	@ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "categories")
	private List<Book> books;

	public long getCatId() {
		return catId;
	}

	public void setCatId(long catId) {
		this.catId = catId;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public String getCatDesc() {
		return catDesc;
	}

	public void setCatDesc(String catDesc) {
		this.catDesc = catDesc;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

}
