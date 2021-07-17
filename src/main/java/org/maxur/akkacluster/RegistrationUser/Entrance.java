package org.maxur.akkacluster.RegistrationUser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.maxur.akkacluster.Users.IUser;
import org.maxur.akkacluster.Users.InfoUser;
import org.maxur.akkacluster.Users.RegisteredUser;

public class Entrance {
	private List<IUser> users;
	private String login;
	private String password;
	
	public Entrance() {
		super();
		readUsers();
	}

	public Entrance(String login, String password) {
		super();
		this.login = login;
		this.password = password;
		readUsers();
	}
	
	public void readUsers() {
		users = new ArrayList<IUser>();
		
		String filePath = "E:\\Загрузки\\akka1\\src\\main\\java\\"
				+ "org\\maxur\\akkacluster\\RegistrationUser\\baseDataUser.txt";
		
		Path path = Paths.get(filePath);
		List<String> list = null;
		
		try {
			list = Files.readAllLines(path);
			int numUser = Integer.parseInt(list.get(0));
			
			for (int i = 1; i < numUser; ++i) {
				String[] recordUser = list.get(i).split(" ");
				users.add(new RegisteredUser(recordUser[0], recordUser[1], 
						recordUser[2], recordUser[3]));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
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

	public InfoUser check() {
		InfoUser infoUser = null;
		
		for (IUser i: users) {
			if (login.hashCode() == Integer.parseInt(i.getLogin()) &&
					password.hashCode() == Integer.parseInt(i.getPassword()) ) {
				infoUser = new InfoUser(i.getName(), i.getSurname());
				break;
			}
		}
			
		return infoUser;
	}
	
}
