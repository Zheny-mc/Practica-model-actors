package org.maxur.akkacluster;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class Main {
	
	public static ActorSystem system = ActorSystem.create("learning2hard");
	public static ActorRef client = system.actorOf(Props.create(Client.class), "client");
	public static ActorRef messager = system.actorOf(Props.create(Messager.class), "messager");
	
	public static void main(String[] args){
		
			
		final Record record = new Record("Practica", 5214);
		
		for (int i = 0; i < 10; ++i) {
			client.tell(new Record(record), ActorRef.noSender());
			messager.tell(MailMessage.create("Hello"), ActorRef.noSender());
		}
		
		//system.terminate();
	}

}
