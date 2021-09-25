/**
 * 
 */
package com.ss.utopia.entity;

/**
 * @author Walter Chang
 *
 */
public class BookingAgent {
	private Book book;
	private Integer id;

	public BookingAgent(Book book, Integer id) {
		super();
		this.id = id;
		this.book = book;
	}

	public BookingAgent() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}
}
