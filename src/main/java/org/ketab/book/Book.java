package org.ketab.book;

import java.sql.Blob;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

import org.ketab.author.Author;
import org.ketab.category.Category;
import org.ketab.publisher.Publisher;
import org.ketab.review.Review;
import org.ketab.user.User;

@Entity
@Table (name = "BOOK")
public class Book {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column (name = "BOOK_ID", nullable=false)
	private Long bookId;
	
	@Column (name = "BOOK_TITLE")
	private String bookTitle;
	
	@Lob
	@Column(name="BOOK_BLOB", length=41943040)
	private byte[] bookBlob;

	@Column (name = "UPLOAD_DATE")
	private Date uploadDate;
	
	@Column(name = "PUBLISH_YEAR")
	private int publishYear;
	
	@Column(name = "BOOK_DESCRIPTION")
	private String bookDesc;
	
	@Column(name = "TAGS")
	private String tags;
	
	@Column(name = "READ_COUNT")
	private int readCount;
	
	@Column(name = "PAGES_COUNT")
	private int pagesCount;
	
	@Column(name = "PUBLISH_PLACE")
	private String publishPlace;
	
	@ManyToMany(cascade = CascadeType.MERGE, mappedBy = "books")
	private List<Author> authors;
	
	@ManyToMany(cascade = CascadeType.REMOVE)
	@JoinTable(	name = "BOOK_CATEGORY",
				joinColumns = @JoinColumn(name="BOOK_ID", referencedColumnName="BOOK_ID"),
				inverseJoinColumns = @JoinColumn(name = "CAT_ID", referencedColumnName = "CAT_ID")
	)
	private List<Category> categories;
	
	@ManyToOne
	@JoinColumn(name="UPLOADER")
	private User uploader;
	
	@ManyToOne
	@JoinColumn(name = "PUBLISHER", referencedColumnName="PUB_ID")
	private Publisher publisher;
	
	@OneToMany(cascade=CascadeType.PERSIST, mappedBy="bookReviewed")
	private List<Review> reviews;
			
	public Book(){
	}
	
	public Book(String title){
		this.bookTitle= title;
	}

	public String getTitle(){
		return this.bookTitle;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public int getPublishYear() {
		return publishYear;
	}

	public void setPublishYear(int publishYear) {
		this.publishYear = publishYear;
	}

	public String getBookDesc() {
		return bookDesc;
	}

	public void setBookDesc(String bookDesc) {
		this.bookDesc = bookDesc;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public int getReadCount() {
		return readCount;
	}

	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}

	public int getPagesCount() {
		return pagesCount;
	}

	public void setPagesCount(int pagesCount) {
		this.pagesCount = pagesCount;
	}

	public String getPublishPlace() {
		return publishPlace;
	}

	public void setPublishPlace(String publishPlace) {
		this.publishPlace = publishPlace;
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public User getUploader() {
		return uploader;
	}

	public void setUploader(User uploader) {
		this.uploader = uploader;
	}

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public void setTitle(String title) {
		this.bookTitle = title;
	}

	public byte[] getBookBlob() {
		return bookBlob;
	}

	public void setBookBlob(byte[] bookBlob) {
		this.bookBlob = bookBlob;
	}
	
}