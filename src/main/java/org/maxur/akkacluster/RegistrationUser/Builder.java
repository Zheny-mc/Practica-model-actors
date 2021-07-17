package org.maxur.akkacluster.RegistrationUser;

import java.util.List;
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
	
	public void enterUser() {
		String login = input("Input login: "); 
		String password = input("Input password: ");
		Entrance entrance = new Entrance(login, password);
		if (entrance.check() != null) {
			System.out.println("Вход в систему...");
			InfoUser info = entrance.check();
			user = new RegisteredUser(info.getName(), info.getSurName());
		} 
		else {
			System.out.println("Ошибка, попробуйте еще раз!");
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
		//return new RegisteredUser("Evgeny", "Bubnov", "1", "2");
	}
	
}
