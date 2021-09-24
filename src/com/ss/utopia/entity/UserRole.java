/**
 * 
 */
package com.ss.utopia.entity;

/**
 * @author Walter Chang
 *
 */
public class UserRole {
	private String id;
	private String name;
	
	public UserRole(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public UserRole() {
		
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
