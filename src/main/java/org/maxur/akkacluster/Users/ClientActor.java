package org.maxur.akkacluster.Users;


import static akka.actor.ActorRef.noSender;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Scanner;

import org.maxur.akkacluster.RegistrationUser.Builder;
import org.maxur.akkacluster.RegistrationUser.StateUser;
import org.maxur.akkacluster.baseData.Record;
import org.maxur.akkacluster.mainMircoService.PrimeWorkerActor;
import org.maxur.akkacluster.packageForDialog.PackChangeRecord;
import org.maxur.akkacluster.packageForDialog.PackPopRecord;
import org.maxur.akkacluster.packageForDialog.PackPushRecord;
import org.maxur.akkacluster.packageForDialog.PackPushUser;
import org.maxur.akkacluster.packageForDialog.PackUpdateClient;
import org.maxur.akkacluster.senderMiroService.Email;
import org.maxur.akkacluster.senderMiroService.MailMessage;
import org.maxur.akkacluster.senderMiroService.Telegram;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;

public class ClientActor extends UntypedAbstractActor {	
	
	private ActorRef worker;
	private ActorRef telegram;
	private ActorRef email;
	
	private Builder builder;
	private IUser user;
	private int job_id_counter = 0;
	
	public static void main(String[] args) throws Exception {
		ActorSystem system = ActorSystem.create("ClientSystem");
        system.actorOf(Props.create(ClientActor.class));
    }
	
	@Override
	public void preStart() {
		System.out.println("start client");
		builder = new Builder(StateUser.REGISTERED);
		builder.build();
		user = builder.getUser();
		
		telegram = getContext().actorOf(Props.create(Telegram.class), "telegram");
      	email = getContext().actorOf(Props.create(Email.class), "email");
		worker = getContext().actorOf(Props.create(PrimeWorkerActor.class), "worker");
		run();
	}
	
	@Override
	public void postStop() throws Exception {
		System.out.println("stop client");
		
        context().system().terminate();
        exit();
	}
	
	@Override
	public void onReceive(Object message) {
		
		//сообщение пришло
		if (message instanceof Map<?, ?>) {
			Map<Integer, Record> records = (Map<Integer, Record>)message;
			user.setRecords(records);
			System.out.println("Обновление списка: " + records.size());
		}
		
		if (message instanceof String) {
			final String ans = (String) message;			
			System.out.println(++job_id_counter + ")Операция " + ans);
		}
		
		unhandled(message);		
	}
	
	private Integer inputMode() {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Input mode:");
		System.out.println("0 - push   record");
		System.out.println("1 - pop    record");
		System.out.println("2 - change record");
		System.out.println("3 - end job");
		
		return sc.nextInt();
	}
	
	private void run() {
		String filePath = "E:\\Загрузки\\akka1\\src\\main\\java\\org\\"
				+ "maxur\\akkacluster\\infomation_user.txt";
		IUser user = getUserFromFile(filePath);
		
		InfoUser infoUser = InfoUser.create(user.getName(), user.getSurname());
		
		getRecords(infoUser);
		
		Integer mode;
		while (true) {
			mode = inputMode();
			
			if (mode == 3) {
				sendMessagers();
				getRecords(infoUser);
				worker.tell(PoisonPill.getInstance(), noSender());
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
				System.out.println("!Error!");
			}
		}

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
		worker.tell(PackPushRecord.create(record.hashCode(), record, infoUser), self());
	}
	
	private void popRecord(InfoUser infoUser) {
		Integer indRecord = 0;
		worker.tell(PackPopRecord.create(getIdRecord(indRecord), infoUser), self());
	}
	
	private void changeRecord(InfoUser infoUser) {
		final Record record1 = new Record("vanga", "pistolet", 2021);
		final Record record2 = new Record("vanga", "pistolet", 2050);
		worker.tell(PackChangeRecord.create(record1.hashCode(), record2.hashCode(), record2, infoUser), self());
	}
	
	private void getRecords(InfoUser infoUser) {
		worker.tell(PackUpdateClient.create(infoUser), self());
	}
	
	private void exit() {
		System.out.println("GoodBuy!");
	}
	
	private void sendMessagers() {
		telegram.tell(MailMessage.create("update"), ActorRef.noSender());
		//email.tell(MailMessage.create("update"), ActorRef.noSender());
	}
}

