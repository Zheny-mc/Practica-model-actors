package org.maxur.akkacluster.Users;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.maxur.akkacluster.baseData.Record;

public abstract class IUser {
	protected String name;
	protected String surname;
	protected Map<Integer, Record> records;
	protected String login;
	protected String password;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public abstract Map<Integer, Record> getRecords();
	
	public void setRecords(Map<Integer, Record> records) {
		this.records = records;
	}
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}

	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public abstract void pushRecord(Integer id, Record record);
	public abstract void popRecord(Integer id);
	public abstract void changeRecord(Integer oldId, Integer newId, Record record);
}
