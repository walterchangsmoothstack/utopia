package com.ss.utopia.menu;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Random;

import com.ss.utopia.service.ConnectionUtil;

public class GenerateRandomData {

	public static final String user = "root";
	public static final String password = "4Bible1234"; // REMEMBER TO DELETE
	public static final String driver = "com.mysql.cj.jdbc.Driver";
	public static final String url = "jdbc:mysql://localhost/utopia";

	public static void send(Connection conn, String params, String table) throws SQLException {
		String stmt = "INSERT INTO "+  table+" VALUES(" + params +")";
			Statement statement = conn.createStatement();
			statement.execute(stmt);
			conn.commit();
	}
	
	public String generateCode() {
		String s = "\"";
		Random random = new Random();
		for(int i =0; i<20; i++) {
			if(Math.random() > 0.5) {
			s+= Math.random() > 0.5 ? Character.toUpperCase((char)(random.nextInt(26)+97)) :
				(char)(random.nextInt(26)+97);
			}
			else {
				s+= random.nextInt(10);
			}
		}
		s+="\"";
		return s;
	}
	
	public String getBoolean() {
		if(Math.random() > 0.5) {
			return "1";
		}
		return "0";
	}
	public String concat(String s, int i) {
		return "\""+s + Integer.toString(i)+"\"";
	}
	public String phone() {
		Random rand = new Random();
		String s = "\"";
		for(int i =0; i<12; i++) {
			if(i == 3 || i==7) s+= '-';
			else {
				s+=(rand.nextInt(10));
			}
		}
		s+="\"";
		return s;
		
		
	}
	public LocalDate getDate() {
		
		Random random = new Random();
		int minDay = (int) LocalDate.of(1950, 1, 1).toEpochDay();
		int maxDay = (int) LocalDate.of(2021, 1, 1).toEpochDay();
		long randomDay = minDay + random.nextInt(maxDay - minDay);

		LocalDate randomBirthDate = LocalDate.ofEpochDay(randomDay);
		return randomBirthDate;

	}

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, user, password);
		conn.setAutoCommit(Boolean.FALSE);
		GenerateRandomData rand = new GenerateRandomData();
		/* Insert bookings */
		for(int i =20; i<=30; i++) {
			String s = Integer.toString(i) + ", "+ rand.getBoolean()+", \"" + rand.generateCode()+"\"";
			//send(conn, s, "booking");
		}

		
		/* Insert passengers */
		for(int i =1; i<=30; i++) {
			String s = Integer.toString(i+100)+", "+Integer.toString(i) +", \"Name"+ Integer.toString(i) +"\",  \"Surname"+ Integer.toString(i)+
					"\", \"" + rand.getDate()+"\", \"" + ((Math.random()>.5)?"male" : "female") +"\", \"address"+Integer.toString(i)+"\"";
			//send(conn, s, "passenger");
		}
		Random random = new Random();
		for(int i = 1; i<20; i++) {
			
			String s = Integer.toString(i)+", "+Integer.toString(random.nextInt(3)+1) +", "+rand.concat("Name", i)+", " + rand.concat("Lastname", i)
			+ ", "+rand.concat("user", i) + ", " +rand.concat("domain_name@", i) +", " + rand.concat("pwd", i)+", " + rand.phone();
			//System.out.println(s);

			//send(conn, s, "user");
		}
		for(int i = 1; i<10; i++) {
			
			String s = Integer.toString(i)+", "+Integer.toString(i);

			//send(conn, s, "booking_agent");
		}
		for(int i = 10; i<20; i++) {
			
			String s = Integer.toString(i)+", "+Integer.toString(i);

			//send(conn, s, "booking_user");
		}
		for(int i = 20; i<=30; i++) {
			
			String s = Integer.toString(i)+", "+rand.concat("domain_name@", i) +", "+rand.phone();

			//send(conn, s, "booking_guest");
		}
		for(int i = 1; i<=30; i++) {
			
			String s = Integer.toString(i)+", "+rand.generateCode()+", "+ rand.getBoolean();

			//send(conn, s, "booking_payment");
		}
		for(int i = 1; i<=30; i++) {
			
			String s = Integer.toString(random.nextInt(4)+1)+", "+ Integer.toString(i);
			//System.out.println(s);
			//send(conn, s, "flight_bookings");
		}
		for(int i = 1; i<15; i++) {
			String s = Integer.toString(i)+", " +Integer.toString(random.nextInt(4)*100+random.nextInt(10)*10);
			//send(conn, s, "airplane_type");

		}
		for(int i = 1; i<15; i++) {
			String s = Integer.toString(i)+", " + Integer.toString(random.nextInt(14)+1);
			//System.out.println(s);
			//send(conn, s, "airplane");
		}
		for(int i = 1; i<15; i++) {
			String s = Integer.toString(400+i)+", " + Integer.toString(random.nextInt(15)+1)+", "+Integer.toString(random.nextInt(14)+1)
			+", \""+rand.getDate()+" 04:"+Integer.toString(random.nextInt(50)+10)+ ":30\", "+
					Integer.toString(random.nextInt(300)) + ", "+Integer.toString(250);
			System.out.println(s);
			send(conn, s, "flight");
		}
		
		
		
		

	}
}
