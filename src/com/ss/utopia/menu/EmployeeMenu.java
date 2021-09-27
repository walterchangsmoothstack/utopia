/**
 * 
 */
package com.ss.utopia.menu;

import java.util.ArrayList;
import java.util.List;

import com.ss.utopia.entity.User;
import com.ss.utopia.entity.UserRole;
import com.ss.utopia.service.AdminPassenger;

/**
 * @author Walter Chang
 *
 */
public class EmployeeMenu extends Menu {
	final static private AdminPassenger admin = new AdminPassenger();
	final private int ADMIN = 1;
	final private int TRAVELER = 2;
	final private int AGENT = 3;

	/* Add and employee. TODO check for valid usernames/emails/phone numbers */
	public void addEmployee() {
		User employeeToAdd = new User();

		System.out.println("1) Administrator");
		System.out.println("2) Agent");
		System.out.println("3) Go Back");
		int choice = readInput(1, 3);
		/* DETERMINE ROLE OF EMPLOYEE */
		if (choice == 3) {
			chooseCategory(1); // return to add menu
		}
		if (choice == 1) {
			employeeToAdd.setRole(new UserRole(ADMIN, "Administrator"));
		} else {
			employeeToAdd.setRole(new UserRole(AGENT, "Agent"));
		}

		List<User> users = createUserList();

		/* Create list of unique values to ensure unique user is created */
		List<String> usernames = new ArrayList<>();
		List<String> emails = new ArrayList<>();
		List<String> phones = new ArrayList<>();
		for (User u : users) {
			usernames.add(u.getUsername());
			emails.add(u.getEmail());
			phones.add(u.getPhone());
		}

		/* DETERMINE FIRST AND LAST NAME */
		System.out.println("Enter first name: ");
		String firstName = validateName();
		System.out.println("Enter last name: ");
		String lastName = validateName();
		lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1).toLowerCase();
		employeeToAdd.setGivenName(firstName);
		employeeToAdd.setFamilyName(lastName);

		/* DETERMINE EMAIL USERNAME PASSWORD AND PHONE */
		System.out.println("Enter a username: ");
		String username = validateUsername(usernames);

		String email = "";
		System.out.println("Enter an email: ");
		email = validateEmail(emails);
		if (email == null) {
			addEmployee(); // return to the beginning of add
			return;
		}
		System.out.println(
				"Enter a password (Must be at least 8 characters and contain at least 1 letter and 1 number): ");
		String password = readInputRegex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
		String phone = "";
		System.out.println("Enter a phone number: ");
		phone = validatePhone(phones);
		/* Update employeeToAdd for insertion */
		employeeToAdd.setUsername(username);
		employeeToAdd.setEmail(email);
		employeeToAdd.setPassword(password);
		employeeToAdd.setPhone(phone);

		System.out.println("------------Confirm User Creation------------");
		printOneUser(employeeToAdd);
		System.out.println("Confirm: y/n");
		if (readYesNo()) {
			System.out.println(admin.addUser(employeeToAdd));
			runProgram();
		} else {
			addEmployee();
		}

	}
	
	/* Reads all users who are administors or agents*/
	public void readEmployee() {
		List<User> users = createUserList();
		for(User u : users) {
			if(u.getRole().getId() != TRAVELER) {
				printOneUser(u);
			}
		}
		System.out.println("1) Go Back");
		if(readInput(1, 1) == 1) {
			runProgram();
		}
	}
	

	/* First stage of update employee, no data saved within*/
	public void updateEmployee() {
		System.out.println("Select Employee to update.");
		User user = selectUserList();
		if (user == null) {
			chooseCategory(3); // return to update selection
		}
		updateEmployeeSave(user); //move to second stage where multiple values can be changed 

	}
/* Second stage of updateEmployee. Prompts user to choose which category to change. Switch
 * Case handles each category and adds them to each other if the user decides to continue changing*/
	public void updateEmployeeSave(User user) {

		List<User> users = createUserList();

		/* Create list of unique values to ensure unique user is created */
		List<String> usernames = new ArrayList<>();
		List<String> emails = new ArrayList<>();
		List<String> phones = new ArrayList<>();
		for (User u : users) {
			usernames.add(u.getUsername());
			emails.add(u.getEmail());
			phones.add(u.getPhone());
		}
		System.out.println("Select category to change.");
		System.out.println("1) First Name");
		System.out.println("2) Last Name");
		System.out.println("3) Role Id");
		System.out.println("4) Username");
		System.out.println("5) Password");
		System.out.println("6) Email");
		System.out.println("7) Phone");
		System.out.println("8) Go Back");
		int choice = readInput(1, 8);
		if (choice == 8) {
			/* the employee to update will be lost in memory*/
			System.out.println("Are you sure? All progress will be lost.");
			if (readYesNo()) {
				updateEmployee();
			} else {
				updateEmployeeSave(user);
			}
			return;
		}

		switch (choice) {
		case 1:
			System.out.println("Enter new first name: ");
			String fname = validateName();
			if (fname.equals(user.getGivenName())) { // If the same name is given, go back to selection
				System.out.println("Cannot change first name to previous name");
				updateEmployeeSave(user);
				return;
			}
			user.setGivenName(fname); //save data
			break;
		case 2:
			System.out.println("Enter new last name: ");
			String lname = validateName();
			if (lname.equals(user.getFamilyName())) { // If the same name is given, go back to selection
				System.out.println("Cannot change first name to previous name");
				updateEmployeeSave(user);
				return;
			}
			user.setGivenName(lname);//save data
			break;
		case 3:
			System.out.println("1) Administrator");
			System.out.println("2) Agent");
			System.out.println("3) Go Back");
			choice = readInput(1, 3);
			if (choice == 3) {// return to previous stage
				updateEmployeeSave(user);
				return;
			}
			if (choice == 1) {
				user.setRole(new UserRole(ADMIN, "Administrator"));//save data
			} else {
				user.setRole(new UserRole(AGENT, "Agent"));//save data
			}
			break;
		case 4:
			System.out.println("Enter a new username: ");
			String username = validateUsername(usernames);
			user.setUsername(username);//save data
			break;

		case 5:
			System.out.println(
					"Enter a password (Must be at least 8 characters and contain at least 1 letter and 1 number): ");
			String password = readInputRegex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
			if (password.equals(user.getPassword())) {
				System.out.println("Cannot set new password to old password.");
				System.out.println("1) Okay");
				if (readInput(1, 1) == 1) { // return to previous stage
					updateEmployeeSave(user);
					return;
				}
			}
			user.setPassword(password);//save data
			break;
		case 6:
			System.out.println("Enter a new email:");
			String email = validateEmail(emails);
			if (email == null) {// return to previous stage
				updateEmployeeSave(user);
				return;
			}
			user.setEmail(email); //save data
			break;

		case 7:
			System.out.println("Enter new phone: ");
			String phone = validatePhone(phones);
			user.setPhone(phone);//save data
			break;
		}
		/* Prompt user to see if he wants to continue changine or submit changes*/
		System.out.println("1) Continue");
		System.out.println("2) Change another value");
		choice = readInput(1, 2);
		if(choice == 2) {
			updateEmployeeSave(user);
			return;
		}
		/* Last review before submitting update*/
		System.out.println("------------Review Employee Update------------");
		printOneUser(user);
		System.out.println(user.getId());

		System.out.println("Confirm: y/n");
		if (readYesNo()) {
			System.out.println(admin.updateUser(user));
			runProgram();
		} else {
			addEmployee();
		}
		
	}
	
	/* Caution-- deleting a user may lead to other keys from different 
	 * tables being destroyed and deleted. Issue warning check before 
	 * executing final deletion.*/
	public void deleteEmployee() {
		System.out.println("Select Employee to delete");
		User user = selectUserList();
		if(user == null) {
			chooseCategory(4);
			return;
		}
		System.out.println("------------Review Employee DELETION------------");
		printOneUser(user);
		System.out.println("Confirm: y/n");
		if(!readYesNo()) {
			chooseCategory(4);
			return;
		}
		System.out.println("************Warning************");
		System.out.println("Deleting a user may lead to data truncation in booking agents and tickets");
		System.out.println("Are you sure you want to delete this user? y/n");
		if(readYesNo()) {
			System.out.println(admin.deleteUser(user));
			runProgram();
			return;
		}
		chooseCategory(4); //return to deletion menu
		
		
		
	}

	
	
	
	/* Create a user list from services */
	public List<User> createUserList() {
		return admin.createUserList().stream().filter((u) -> u.getRole().getId() != TRAVELER).toList();
	}
	
	
	/* Displays the users and handles selection of all users. Ignores travelers.*/
	public User selectUserList() {
		List<User> users = createUserList();
		int index = 1; //index of selection
		for (User u : users) {
			
			System.out.println((index++) + ") First Name: " + u.getGivenName() + "   Last Name: " + u.getFamilyName()
					+ "   User Role: " + u.getRole().getId());
			System.out.println(
					"  Username: " + u.getUsername() + "   Email: " + u.getEmail() + "   Phone: " + u.getPhone());
		}
		System.out.println(index + ") Go Back");
		int choice = readInput(1, index);
		if (choice == index) {
			return null;
		}
		return users.get(choice-1);
	}


	
	

}
