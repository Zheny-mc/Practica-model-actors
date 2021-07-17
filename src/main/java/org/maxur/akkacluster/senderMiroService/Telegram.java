package org.maxur.akkacluster.senderMiroService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.maxur.akkacluster.Users.ClientActor;
import org.maxur.akkacluster.Users.IUser;
import org.maxur.akkacluster.Users.RegisteredUser;
import org.maxur.akkacluster.baseData.Record;
import org.maxur.akkacluster.baseData.SQLdataBaseActor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class Telegram extends IMessager {
	
	private ActorRef repository;
	
	public static void main(String[] args) throws Exception {
        startSystem();
    }

    private static void startSystem() {
    	ActorSystem system = ActorSystem.create("learning");
    	system.actorOf(Props.create(Telegram.class), "telegram");
    }
	
    @Override
    public void preStart() throws Exception {
    	super.preStart();
    	name = "Telegram";
		users = new ArrayList<IUser>();
		users.add(new RegisteredUser("Евгений", "Бубнов"));
		users.add(new RegisteredUser("Вова", "Смолков"));
		repository = getContext().actorOf(Props.create(SQLdataBaseActor.class), "repository");
		run();
    }
		
    @Override
    public void postStop() throws Exception {
    	super.postStop();
    }
    
	@Override
	public void sendAll() {
		for (IUser i: users) {
			System.out.println("Отправлено: " + i.getRecords().size() + " записи\n" + 
					"от:" + name + "\n" +  "к: "+ i.getName() + " " + i.getSurname() +"\n"); 
		}
		
		//иммитация большого кол-ва пользователей
		try { Thread.sleep(100); } 
		catch (InterruptedException e) { e.printStackTrace(); }
	}

	@Override
	public void onReceive(Object message) {
		
		if (message instanceof Map<?, ?>) {			
			final Map<Integer, Record> records = (Map<Integer, Record>)message;
			
			Iterator it = records.entrySet().iterator();
			
			while (it.hasNext()) {
				Entry entry = (Entry) it.next();
				
				for (IUser j: users) {
					Integer key = (Integer) entry.getKey();
					Record value = (Record) entry.getValue();
					j.pushRecord(key, value);
				}
			}
		
			sendAll();
		}
		
		unhandled(message);
	}
	
	private void run() {
		repository.tell(MailMessage.create("update"), getSelf());
	}
}
