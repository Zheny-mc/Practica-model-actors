package org.maxur.akkacluster.Users;

import java.util.HashMap;
import java.util.Map;

import org.maxur.akkacluster.baseData.Record;

public class Admin extends IUser {

	public Admin(String name, String surname) {
		this.name = name;
		this.surname = surname;
		records = new HashMap<Integer, Record>();
	}
	
	@Override
	public void pushRecord(Integer id, Record record) {
		records.put(id, record);
	}
	
	@Override
	public void popRecord(Integer id) {
		try {
			records.remove(id);
		} catch(NullPointerException  e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public void changeRecord(Integer oldId, Integer newId, Record record) {
		popRecord(oldId);
		pushRecord(newId, record);
	}

	@Override
	public Map<Integer, Record> getRecords() {
		// TODO Auto-generated method stub
		return null;
	}

}
