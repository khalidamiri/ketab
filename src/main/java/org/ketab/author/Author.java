package org.ketab.author;

import java.util.List;
import javax.persistence.*;

import org.ketab.book.Book;

@Entity
@Table(name = "AUTHOR")
public class Author {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "AUTHR_ID")
	private Long authrId;
	
	@Column(name = "AUTHR_NAME")
	private String authrName;

	@Column(name = "AUTHR_BIO")
	private String authrBio;
	
	@ManyToMany
	@JoinTable(	name = "BOOK_AUTHOR", 
				joinColumns = @JoinColumn(name = "AUTHR_ID", referencedColumnName = "AUTHR_ID"),
				inverseJoinColumns = @JoinColumn(name="BOOK_ID", referencedColumnName="BOOK_ID")
	)
	private List<Book> books;

	public Author(){	
	}

	public Long getAuthrId() {
		return authrId;
	}

	public void setAuthrId(Long authrId) {
		this.authrId = authrId;
	}

	public String getAuthrName() {
		return authrName;
	}

	public void setAuthrName(String authrName) {
		this.authrName = authrName;
	}

	public String getAuthrBio() {
		return authrBio;
	}

	public void setAuthrBio(String authrBio) {
		this.authrBio = authrBio;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

}