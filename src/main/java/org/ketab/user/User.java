
package org.ketab.user;

import java.io.UnsupportedEncodingException;
import org.ketab.utils.Utils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.List;
import javax.persistence.*;
import org.ketab.book.Book;
import org.ketab.review.Review;

import sun.misc.BASE64Encoder;

@Entity
@Table(name = "USER")
public class User {

	@Id
	@Column(name="USER_ID", unique=true, nullable=false)
	private String userId;
	
	@Column(name = "FIRST_NAME", length=50)
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastname;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "DATE_JOINED")
	private Date dateJoined;

	@Column(name = "READ_COUNT")
	private int readCount;
	
	@OneToMany(cascade=CascadeType.PERSIST, mappedBy="uploader")
	private List<Book> booksUploaded;
	
	@OneToMany(cascade=CascadeType.PERSIST, mappedBy="reviewer")
	private List<Review> reviews;
	
	@ManyToMany(cascade=CascadeType.PERSIST, fetch=FetchType.LAZY)
	@JoinTable(
			name = "USER_ROLE",
			joinColumns = @JoinColumn(name="USER_ID", referencedColumnName="USER_ID"),
			inverseJoinColumns = @JoinColumn(name="ROLE_ID", referencedColumnName="ROLE_ID")
			)
	private List<Role> rolesAssigned;
	
	public void setPassword(String pw) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		Utils utils = new Utils();
		this.password = utils.encrypt(pw);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDateJoined() {
		return dateJoined;
	}

	public void setDateJoined(Date dateJoined) {
		this.dateJoined = dateJoined;
	}

	public int getReadCount() {
		return readCount;
	}

	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}

	public List<Book> getBooksUploaded() {
		return booksUploaded;
	}

	public void setBooksUploaded(List<Book> booksUploaded) {
		this.booksUploaded = booksUploaded;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public List<Role> getRolesAssigned() {
		return rolesAssigned;
	}

	public void setRolesAssigned(List<Role> rolesAssigned) {
		this.rolesAssigned = rolesAssigned;
	}

	public String getPassword() {
		return password;
	}
	
}