/**
 * 
 */
package com.ss.utopia.entity;

/**
 * @author Walter Chang
 *
 */
public class Book {

	private Integer id;
	private Boolean isActive;
	private String confirmationCode;

	public Book(Integer id, Boolean isActive, String confirmationCode) {
		super();
		this.id = id;
		this.isActive = isActive;
		this.confirmationCode = confirmationCode;
	}
	public Book() {
		
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getConfirmationCode() {
		return confirmationCode;
	}

	public void setConfirmationCode(String confirmationCode) {
		this.confirmationCode = confirmationCode;
	}

}
