/**
 * 
 */
package com.ss.utopia.service;

import com.ss.utopia.entity.User;
import com.ss.utopia.entity.UserRole;
import com.ss.utopia.service.AdminServices.Service;

/**
 * @author Walter Chang
 *
 */
public class AdminUser {

	ConnectionUtil conn = new ConnectionUtil();
	
	public String addUser(User user) {
		AdminServices admin = new AdminServices();
		return admin.add(user, Service.USER, conn);
	}
	public String readUser() {
		AdminServices admin = new AdminServices();
		return admin.read(null, Service.USER, conn);
	}
	public String readUser(UserRole role) {
		AdminServices admin = new AdminServices();
		return admin.read(role, Service.USER, conn);
	}
	public String updateUser(User user) {
		if(user == null || user.getId() ==null) {
			return "Unable to update USER successfully";
		}
		AdminServices admin = new AdminServices();
		return admin.update(user, Service.USER, conn);
	}
	public String deleteUser(User user) {
		AdminServices admin = new AdminServices();
		return admin.delete(new Object[] {user}, Service.USER, conn);
	}
}
