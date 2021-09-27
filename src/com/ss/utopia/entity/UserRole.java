/**
 * 
 */
package com.ss.utopia.entity;

/**
 * @author Walter Chang
 *
 */
public class UserRole {
	private Integer id;
	private String name;
	
	public UserRole(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public UserRole() {
		
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	

}
