package org.maxur.akkacluster;

import static akka.actor.ActorRef.noSender;

import org.maxur.akkacluster.Users.ClientActor;
import org.maxur.akkacluster.Users.InfoUser;
import org.maxur.akkacluster.baseData.Record;
import org.maxur.akkacluster.packageForDialog.PackChangeRecord;
import org.maxur.akkacluster.packageForDialog.PackPopRecord;
import org.maxur.akkacluster.packageForDialog.PackPushRecord;
import org.maxur.akkacluster.packageForDialog.PackUpdateClient;
import org.maxur.akkacluster.senderMiroService.Email;
import org.maxur.akkacluster.senderMiroService.MailMessage;
import org.maxur.akkacluster.senderMiroService.Telegram;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.actor.TypedActor.PreStart;

public class Main {
	
	public static ActorSystem system = ActorSystem.create("learning");
	public static ActorRef client = system.actorOf(Props.create(ClientActor.class), "client");
	//public static ActorRef telegram = system.actorOf(Props.create(Telegram.class), "telegram");
	//public static ActorRef email = system.actorOf(Props.create(Email.class), "email");
	
	public static void main(String[] args) {
		InfoUser infoUser = InfoUser.create("Evgeny", "Bubnov");
		client.tell(PackUpdateClient.create(infoUser), ActorRef.noSender());
		
		for (int i = 0; i < 1; ++i) {
			final Record record1 = new Record("vanga", "pistolet", i);
			//final Record record2 = new Record("vanga", "pistolet", 1010);
			
			client.tell(PackPushRecord.create(record1.hashCode(), record1, infoUser), ActorRef.noSender());
			
			//client.tell(PackChangeRecord.create(record1.hashCode(), record2.hashCode(), record2), ActorRef.noSender());
			//client.tell(PackPopRecord.create(record1.hashCode()), ActorRef.noSender());
			//telegram.tell(MailMessage.create("update"), ActorRef.noSender());
			//email.tell(MailMessage.create("update"), ActorRef.noSender());
		}
		
		client.tell(PackUpdateClient.create(infoUser), ActorRef.noSender());
		
		//try { Thread.sleep(10000L); } 
		//catch (InterruptedException e) { e.printStackTrace(); }
		
		//system.terminate();
	}
	
}
