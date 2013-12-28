package org.ketab.publisher;

import java.util.List;
import javax.persistence.*;

import org.ketab.book.Book;

@Entity
@Table(name="PUBLISHER")
public class Publisher {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="PUB_ID")
	private Long pubId;
	
	@Column(name="PUB_NAME")
	private String pubName;
	
	@Column(name="PUB_DESC")
	private String pubDesc;
	
	@OneToMany(mappedBy="publisher", targetEntity=Book.class, fetch=FetchType.LAZY)
	private List<Book> booksPublished;

	public Long getPubId() {
		return pubId;
	}

	public void setPubId(Long pubId) {
		this.pubId = pubId;
	}

	public String getPubName() {
		return pubName;
	}

	public void setPubName(String pubName) {
		this.pubName = pubName;
	}

	public String getPubDesc() {
		return pubDesc;
	}

	public void setPubDesc(String pubDesc) {
		this.pubDesc = pubDesc;
	}

	public List<Book> getBooksPublished() {
		return booksPublished;
	}

	public void setBooksPublished(List<Book> booksPublished) {
		this.booksPublished = booksPublished;
	}

}