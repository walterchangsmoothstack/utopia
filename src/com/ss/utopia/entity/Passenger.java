/**
 * 
 */
package com.ss.utopia.entity;

import java.sql.Date;

/**
 * @author Walter Chang
 *
 */
public class Passenger {
	private Integer id;
	private Book bookId;
	private String givenName;
	private String familyName;
	private Date dob;
	private String gender;
	private String address;

	public Passenger(Integer id, Book bookId, String givenName, String familyName, Date dob, String gender,
			String address) {
		super();
		this.id = id;
		this.bookId = bookId;
		this.givenName = givenName;
		this.familyName = familyName;
		this.dob = dob;
		this.gender = gender;
		this.address = address;
	}

	public Passenger() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Book getBookId() {
		return bookId;
	}

	public void setBookId(Book bookId) {
		this.bookId = bookId;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
