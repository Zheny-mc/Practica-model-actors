package org.maxur.akkacluster.RegistrationUser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import org.maxur.akkacluster.Users.IUser;
import org.maxur.akkacluster.Users.InfoUser;
import org.maxur.akkacluster.Users.RegisteredUser;

public class Builder {
	private StateUser stateUser;
	private Scanner scaner = new Scanner(System.in);
	private IUser user;
	
	public Builder(StateUser stateUser) {
		this.stateUser = stateUser;
	}
	
	private String input(String description) {
		System.out.print(description);
		return scaner.next();
	}
	
	public void registerUser() {
		String name = input("Input name: ");
		String surName = input("Input surName: ");
		String login = input("Input login: "); 
		String password = input("Input password: ");
		Regisration reg = Regisration.create(name, surName, login, password);
		if (reg.getUser() != null)
			System.out.println("Пользователь зарегестрирован");
		else
			System.out.println("Ошибка, попробуйте еще раз!");
	}
	
	private IUser getUserFromFile(String filePath) {
		Path path = Paths.get(filePath);
		String str = "";
		
		try {
			str = Files.readString(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		String[] listFieldsUser = str.split(" ");
		IUser tmpUser = new RegisteredUser(listFieldsUser[0], 
				listFieldsUser[1], listFieldsUser[2], listFieldsUser[3]);
		return tmpUser;
	}
	
	public void enterUser() {
		String filePath = "E:\\Загрузки\\akka1\\src\\main\\java\\org\\"
				+ "maxur\\akkacluster\\infomation_user.txt";
		IUser tmpUser = getUserFromFile(filePath);
		
		while (true) {
			System.out.println("Hello, " + tmpUser.getName());
			String login = tmpUser.getLogin();
			String password = input("Input password: ");
			Entrance entrance = new Entrance(login, password);
			if (entrance.check() != null) {
				System.out.println("Вход в систему...");
				InfoUser info = entrance.check();
				user = new RegisteredUser(info.getName(), info.getSurName());
				break;
			} 
			else {
				System.out.println("Ошибка, попробуйте еще раз!");
			}
		}
			
	}
	
	public void build() {
		switch(stateUser) {
		case UNREGISTERED:
			registerUser();
			break;
		case REGISTERED:
			enterUser();
			break;
		}
	}

	public IUser getUser() {
		return user;
	}
	
}
