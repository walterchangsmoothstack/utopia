package com.ss.utopia.adminTests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.ss.utopia.entity.User;
import com.ss.utopia.entity.UserRole;
import com.ss.utopia.service.AdminUser;

public class userTest {
	
	User user = new User(20, new UserRole(2, null), "a", "b", "username","asdf","1234","1234");
	AdminUser admin = new AdminUser();
	@Test
	public void testAddUser() {
		//System.out.println(admin.addUser(user));
	}
	@Test
	public void testDeleteUser() {
		user.setId(20);
		//assertEquals("Deleted USER successfully", admin.deleteUser(user));
	}
	@Test
	public void readUsers() {
		//System.out.println(admin.readUser());
	}
	@Test
	public void testReadUsersRole() {
		System.out.println(admin.readUser(new UserRole(2, null)));
	}
	@Test
	public void updateUser() {
		user.setId(21);
		user.setEmail("wjc011@ucsd.edu");
		assertEquals("Updated USER successfully", admin.updateUser(user));
		
	}
}
