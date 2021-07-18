package org.maxur.akkacluster.baseData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import org.maxur.akkacluster.Users.IUser;
import org.maxur.akkacluster.Users.InfoUser;
import org.maxur.akkacluster.Users.RegisteredUser;

import java.util.Map.Entry;

public class SQLdataBase implements IBaseData {
	private final Integer MaxSize;
	private Integer curSize;
	private Map<Integer, Record> data;
	private Map<InfoUser, IUser> users;
	private Integer sizeBlockUpdate;

	public static SQLdataBase create() {
		return new SQLdataBase();
	}
	
	public SQLdataBase() {
		data = new TreeMap<Integer, Record>();
		users = new HashMap<InfoUser, IUser>();
		MaxSize = 1000;
		curSize = 0;
		sizeBlockUpdate = 0;
		readBase();
	}
	
	@Override
	public Map<Integer, Record> getData() {
		compilData();
		return data;
	}
	
	@Override
	public Map<InfoUser, IUser> getUsers() {
		return users;
	}
	
	public void compilData() {
		data.clear();
		List<IUser> listUser = new ArrayList<IUser>(users.values());
		
		for (IUser i: listUser)
			data.putAll(i.getRecords());
	}
	
	@Override
	public void readBase() {
		String filePath = "E:\\Загрузки\\akka1\\src\\main\\java"
				+ "\\org\\maxur\\akkacluster\\baseData\\baseData.txt";
		File file = new File(filePath);
		
		try(Scanner scanner = new Scanner(file)) {
			Integer numUsers = scanner.nextInt();
			
			for (int i = 0; i < numUsers; ++i) {
				String name = scanner.next();
				String surName = scanner.next();
				IUser user = new RegisteredUser(name, surName);
				users.put(InfoUser.create(user), user);
				
				Integer numRecordUser = scanner.nextInt();
				curSize += numRecordUser;
				for (int j = 0; j < numRecordUser; ++j) { 
					Integer key = scanner.nextInt(); 
					
					Record value = new Record();
					value.setName(scanner.next());
					value.setTopic(scanner.next());
					value.setAudienceNumber(scanner.nextInt());
					
					user.pushRecord(key, value);
				}
			}
			
		}
		catch(IOException ex){
		    System.out.println(ex.getMessage());
		}
		
	}
	
	@Override
	public void update() {
		String filePath = "E:\\Загрузки\\akka1\\src\\main\\java\\"
				+ "org\\maxur\\akkacluster\\baseData\\baseData.txt";
		
		try(FileWriter writer = new FileWriter(filePath, false)) {
			
			//запись кол-во пользователей
			writer.write(users.size() + "\n");
			
			List<IUser> listUser = new ArrayList<IUser>(users.values());
			for (IUser i: listUser) {
				//запись информации о пользователе
				String infoUser = "";
				infoUser += i.getName() + " ";
				infoUser += i.getSurname() + " ";
				infoUser += i.getRecords().size() + "\n";
				writer.write(infoUser);
				
				//запись записей пользователя
				Iterator itr = i.getRecords().entrySet().iterator();
				
				while (itr.hasNext()) {
					Entry entry = (Entry)itr.next();
					
					String key = ((Integer)entry.getKey()).toString();
					Record value = ((Record)entry.getValue());
					
					String textRecord = " " + value.getName() + 
										" " + value.getTopic() + 
								" " + value.getAudienceNumber() + "\n";
					
					writer.write(key);
		            writer.write(textRecord);
		            
		            writer.flush();
				}
			}
			
        } catch(IOException ex) {
            System.out.println(ex.getMessage());
        } 
	}
	
	@Override
	public void pushRecord(Integer id, Record record, InfoUser infoUser) {
		
		if (curSize < MaxSize) {
			IUser user = users.get(infoUser);
			user.pushRecord(id, record);
			
			update();
			/*
			sizeBlockUpdate++;
			if (sizeBlockUpdate >= MaxSize / 200) {
				//update();
				sizeBlockUpdate = 0;
			}
			*/
			
		} else {
			System.out.println("База данных переполнена!!!");
		}
		
		try { Thread.sleep(10); } 
		catch (InterruptedException e) { e.printStackTrace(); }	
		
	}

	@Override
	public void popRecord(Integer id, InfoUser infoUser) {
		try {
			IUser user = users.get(infoUser);
			user.popRecord(id);
			
			update();
			/*
			sizeBlockUpdate++;
			if (sizeBlockUpdate >= MaxSize / 3) {
				//update();
				sizeBlockUpdate = 0;
			}
			*/
			
		} catch(NullPointerException  e) {
			System.out.println(e.getMessage());
		}
		
		try { Thread.sleep(2); } 
		catch (InterruptedException e) { e.printStackTrace(); }	
	}

	@Override
	public void changeRecord(Integer oldId, Integer newId, Record record, InfoUser infoUser) {
		popRecord(oldId, infoUser);
		pushRecord(newId, record, infoUser);
		update();
	}

	@Override
	public void pushUser(InfoUser infoUser) {
		users.put(infoUser, new RegisteredUser(infoUser.getName(), infoUser.getSurName()));
		update();
	}
}
