/**
 * 
 */
package com.ss.utopia.entity;

/**
 * @author Walter Chang TO-DO: Encrypt password eventually
 */
public class User {
	private Integer id;
	private UserRole role;
	private String givenName;
	private String familyName;
	private String username;
	private String email;
	private String password;
	private String phone;

	public User(Integer id, UserRole role, String givenName, String familyName, String username, String email,
			String password, String phone) {
		super();
		this.id = id;
		this.role = role;
		this.givenName = givenName;
		this.familyName = familyName;
		this.username = username;
		this.email = email;
		this.password = password;
		this.phone = phone;
	}

	public User() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
