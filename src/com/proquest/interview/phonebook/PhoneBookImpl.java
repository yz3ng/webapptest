package com.proquest.interview.phonebook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.proquest.interview.util.DatabaseUtil;

public class PhoneBookImpl implements PhoneBook {
	private List<Person> people = new ArrayList<Person>(); //Change to private
	
	@Override
	public void addPerson(Person newPerson) {
		people.add(newPerson);
	}
	
	@Override
	public void removePerson(Person newPerson) {
		people.remove(newPerson);
	}
	
	@Override
	public Person findPerson(String firstName, String lastName) {
		for(Person aPerson : people){
			if (firstName.equalsIgnoreCase(aPerson.firstName) && lastName.equalsIgnoreCase(aPerson.lastName)){
				return aPerson;		
			}
		}
		System.out.println("Can't find the person named " + firstName + lastName);
		return null;
	}
	@Override
	public String DisplayPhoneBook(){
		String phoneBookInfo = "";
		for (Person p : people){
			phoneBookInfo += p.firstName + " " + p.lastName + ", " + p.phoneNumber
					+ ", " + p.address + "\n";
					
		}
		return phoneBookInfo;
	}
	
	public void addPersonToDatabase(Person newPerson){
		Connection conn = null;
		try {
			conn = DatabaseUtil.getConnection();
			String insertQuery = "INSERT INTO PHONEBOOK (NAME, PHONENUMBER, ADDRESS) VALUES(?,?,?)";
			
			PreparedStatement statement = conn.prepareStatement(insertQuery);
			statement.setString(1, newPerson.firstName + " " + newPerson.lastName);
			statement.setString(2, newPerson.phoneNumber);
			statement.setString(3, newPerson.address);
			
			statement.executeUpdate();
			statement.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.print("DatabaseError:failed to insert new person");
			e.printStackTrace();
		}finally {
			try {
				if(conn != null){
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		DatabaseUtil.initDB();  //You should not remove this line, it creates the in-memory database
		
		PhoneBookImpl pBookImpl = new PhoneBookImpl();
		
		/* TODO: create person objects and put them in the PhoneBook and database
		 * John Smith, (248) 123-4567, 1234 Sand Hill Dr, Royal Oak, MI
		 * Cynthia Smith, (824) 128-8758, 875 Main St, Ann Arbor, MI
		*/ 
		Person jSmith = new Person("John", "Smith","(248) 123-4567", 
				"1234 Sand Hill Dr, Royal Oak, MI");
		Person cSmith = new Person("Cynthia", "Smith", "(824) 128-8758",
				"875 Main St, Ann Arbor, MI");
		
		pBookImpl.addPerson(jSmith);
		pBookImpl.addPerson(cSmith);
		
		// TODO: print the phone book out to System.out
		System.out.println("Phone Book");
		System.out.println(pBookImpl.DisplayPhoneBook());	
		
		// TODO: find Cynthia Smith and print out just her entry
		Person findResult = pBookImpl.findPerson("Cynthia", "Smith");
		System.out.println(findResult.firstName+ " " + findResult.lastName + ", " + findResult.phoneNumber
				+ ", " + findResult.address + "\n");
		
		// TODO: insert the new person objects into the database
		pBookImpl.addPersonToDatabase(jSmith);
		pBookImpl.addPersonToDatabase(cSmith);
	}
}
