package com.proquest.interview.phonebook;

public interface PhoneBook {
	/**
     * Find a person by first name and last name
     * 
     * @param firstName, lastName
     * @return Person
     */
	public Person findPerson(String firstName, String lastName);
	
	/**
     * Add a new person
     * 
     * @param newPerson
     */
	public void addPerson(Person newPerson);
	
	/**
     * Remove a person from the phone book
     * 
     * @param newPerson
     */
	public void removePerson(Person person);
	
	/**
     * Display the phone book
     * 
     * @return String
     */
	public String DisplayPhoneBook();
	
}
