package org.maxur.akkacluster.RegistrationUser;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.maxur.akkacluster.Users.IUser;
import org.maxur.akkacluster.Users.RegisteredUser;
import org.maxur.akkacluster.baseData.Record;

public class Regisration {
	private IUser user;
	
	public static Regisration create(String name, String surName, 
			String login, String password) {
		return new Regisration(name, surName, login, password);
	}
	
	private Regisration(String name, String surName, 
			String login, String password) {
		user = new RegisteredUser(name, surName, login, password);
		writeUser();
	}
	
	public IUser getUser() {
		return user;
	}
	
	private void writeUser() {
		String filePath = "E:\\Загрузки\\akka1\\src\\main\\java\\"
				+ "org\\maxur\\akkacluster\\RegistrationUser\\baseDataUser.txt";
		
		Path path = Paths.get(filePath);
		List<String> list = null;
		
		try {
			//-------------чтение----------------
			list = Files.readAllLines(path);
			//-------------добавление записи----------------
			Integer numUser = Integer.parseInt(list.get(0)) + 1;
			list.set(0, numUser.toString());
			String recordUser = user.getName() + " " + user.getSurname() + " " +  
					user.getLogin().hashCode() + " " + user.getPassword().hashCode();
			list.add(recordUser);
			//--------------запись в файл--------------------
			String text = "";
			for (String i : list)
				text += i + "\n";
				
			Files.writeString(path, text);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
