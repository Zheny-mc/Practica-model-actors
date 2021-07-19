package org.maxur.akkacluster.panel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import org.maxur.akkacluster.RegistrationUser.ActionsUser;
import org.maxur.akkacluster.Users.ClientActor;
import org.maxur.akkacluster.Users.IUser;
import org.maxur.akkacluster.Users.InfoUser;
import org.maxur.akkacluster.Users.RegisteredUser;
import org.maxur.akkacluster.baseData.Record;
import org.maxur.akkacluster.packageForDialog.PackChangeRecord;
import org.maxur.akkacluster.packageForDialog.PackPopRecord;
import org.maxur.akkacluster.packageForDialog.PackPushRecord;
import org.maxur.akkacluster.packageForDialog.PackPushUser;
import org.maxur.akkacluster.packageForDialog.PackUpdateClient;
import org.maxur.akkacluster.senderMiroService.Email;
import org.maxur.akkacluster.senderMiroService.Telegram;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class Menu {

	private ActorSystem system;
	private ActorRef client;
	private ActorRef telegram;
	private ActorRef email;
	
	public Menu() {
		system = ActorSystem.create("learning");
		client = system.actorOf(Props.create(ClientActor.class), "client");
		//telegram = system.actorOf(Props.create(Telegram.class), "telegram");
		//email = system.actorOf(Props.create(Email.class), "email");
	}
	
	private Integer inputMode() {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Input mode:");
		System.out.println("0 - push   record");
		System.out.println("1 - pop    record");
		System.out.println("2 - change record");
		System.out.println("3 - get    record");
		System.out.println("4 - exit");
		
		return sc.nextInt();
	}
	
	public void run() {
		String filePath = "E:\\Загрузки\\akka1\\src\\main\\java\\org\\"
				+ "maxur\\akkacluster\\infomation_user.txt";
		IUser user = getUserFromFile(filePath);
		
		InfoUser infoUser = InfoUser.create(user.getName(), user.getSurname());
		
		getRecords(infoUser);
		
		Integer mode;
		while (true) {
			mode = inputMode();
			
			if (mode == 4) {
				exit(infoUser);
				break;
			}
			
			switch (mode) {
			case 0:
				pushRecord(infoUser);
				break;
			case 1:
				popRecord(infoUser);
				break;
			case 2:
				changeRecord(infoUser);
				break;
			default:
				System.out.println("Error!");
			}
		}
		
		getRecords(infoUser);
		
		try { Thread.sleep(60000L); } 
		catch (InterruptedException e) { e.printStackTrace(); }
		
		system.terminate();
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
		IUser user = new RegisteredUser(listFieldsUser[0], 
				listFieldsUser[1], listFieldsUser[2], listFieldsUser[3]);
		return user;
	}
	
	private Integer getIdRecord(Integer indRecord) {
		final Record record = new Record("vanga", "pistolet", 2021);
		return record.hashCode();
	}
	
	private void pushRecord(InfoUser infoUser) {
		final Record record = new Record("vanga", "pistolet", 2021);
		client.tell(PackPushRecord.create(record.hashCode(), record, infoUser), ActorRef.noSender());
	}
	
	private void popRecord(InfoUser infoUser) {
		Integer indRecord = 0;
		client.tell(PackPopRecord.create(getIdRecord(indRecord), infoUser), ActorRef.noSender());
	}
	
	private void changeRecord(InfoUser infoUser) {
		final Record record1 = new Record("vanga", "pistolet", 2021);
		final Record record2 = new Record("vanga", "pistolet", 2050);
		client.tell(PackChangeRecord.create(record1.hashCode(), record2.hashCode(), record2, infoUser), ActorRef.noSender());
	}
	
	private void getRecords(InfoUser infoUser) {
		client.tell(PackUpdateClient.create(infoUser), ActorRef.noSender());
	}
	
	private void exit(InfoUser infoUser) {
		System.out.println("GoodBuy, " + infoUser + "!");
	}

}
