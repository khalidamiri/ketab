package org.ketab.review;

import java.sql.Date;

import javax.persistence.*;

import org.ketab.book.Book;
import org.ketab.user.User;

@Entity
@Table(name="REVIEW")
public class Review {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="REV_ID", nullable=false)
	private Long revId;
	
	@Column(name="REV_TITLE")
	private String revTitle;
	
	@Column(name="REV_CONTENT")
	private String revContent;
	
	@Column(name="REV_RATING")
	private String revRating;
	
	@Column(name="REV_DATE")
	private Date revDate;
	
	@ManyToOne
	@JoinColumn(name="REVIEWER")
	private User reviewer;
	
	@ManyToOne
	@JoinColumn(name="BOOK_REVIEWED")
	private Book bookReviewed;

	public Long getRevId() {
		return revId;
	}

	public void setRevId(Long revId) {
		this.revId = revId;
	}

	public String getRevTitle() {
		return revTitle;
	}

	public void setRevTitle(String revTitle) {
		this.revTitle = revTitle;
	}

	public String getRevContent() {
		return revContent;
	}

	public void setRevContent(String revContent) {
		this.revContent = revContent;
	}

	public String getRevRating() {
		return revRating;
	}

	public void setRevRating(String revRating) {
		this.revRating = revRating;
	}

	public Date getRevDate() {
		return revDate;
	}

	public void setRevDate(Date revDate) {
		this.revDate = revDate;
	}

	public User getReviewer() {
		return reviewer;
	}

	public void setReviewer(User reviewer) {
		this.reviewer = reviewer;
	}

	public Book getBookReviewed() {
		return bookReviewed;
	}

	public void setBookReviewed(Book bookReviewed) {
		this.bookReviewed = bookReviewed;
	}
	
}