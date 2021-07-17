package org.maxur.akkacluster.Users;


import java.util.Map;

import org.maxur.akkacluster.RegistrationUser.Builder;
import org.maxur.akkacluster.RegistrationUser.StateUser;
import org.maxur.akkacluster.baseData.Record;
import org.maxur.akkacluster.mainMircoService.PrimeWorkerActor;
import org.maxur.akkacluster.packageForDialog.PackChangeRecord;
import org.maxur.akkacluster.packageForDialog.PackPopRecord;
import org.maxur.akkacluster.packageForDialog.PackPushRecord;
import org.maxur.akkacluster.packageForDialog.PackUpdateClient;


import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;

public class ClientActor extends UntypedAbstractActor {	

	private ActorRef worker;
	//------------------------------------
	private Builder builder;
	private IUser user;	
	private int job_id_counter = 0;
	
	public static void main(String[] args) throws Exception {
        startSystem();
    }

    private static void startSystem() {
    	ActorSystem system = ActorSystem.create("learning");
    	system.actorOf(Props.create(ClientActor.class), "client");
    }
	
	@Override
	public void preStart() {
		builder = new Builder(StateUser.REGISTERED);
		builder.build();
		user = builder.getUser();
		
		worker = getContext().actorOf(Props.create(PrimeWorkerActor.class), "worker");
		run();
	}
	
	@Override
	public void postStop() throws Exception {
		super.postStop();
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
			System.out.println("Операция " + ans);
			System.out.printf("work%d: \n", ++job_id_counter);
		}
		
		unhandled(message);		
	}
	
	private void run() {
		InfoUser infoUser = InfoUser.create(user.getName(), user.surname);
		worker.tell(PackUpdateClient.create(infoUser), getSelf());
		
		for (int i = 0; i < 1; ++i) {
			final Record record1 = new Record("vanga", "pistolet", i);
			//final Record record2 = new Record("vanga", "pistolet", 1010);
			
			worker.tell(PackPushRecord.create(record1.hashCode(), record1, infoUser), getSelf());
			
			//client.tell(PackChangeRecord.create(record1.hashCode(), record2.hashCode(), record2, infoUser), ActorRef.noSender());
			//client.tell(PackPopRecord.create(record1.hashCode(), infoUser), ActorRef.noSender());
		}
		
		worker.tell(PackUpdateClient.create(infoUser), getSelf());
    }
	
}

