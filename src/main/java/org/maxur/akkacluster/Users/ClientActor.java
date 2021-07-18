package org.maxur.akkacluster.Users;


import java.util.Map;

import org.maxur.akkacluster.RegistrationUser.Builder;
import org.maxur.akkacluster.RegistrationUser.StateUser;
import org.maxur.akkacluster.baseData.Record;
import org.maxur.akkacluster.mainMircoService.PrimeWorkerActor;
import org.maxur.akkacluster.packageForDialog.PackChangeRecord;
import org.maxur.akkacluster.packageForDialog.PackPopRecord;
import org.maxur.akkacluster.packageForDialog.PackPushRecord;
import org.maxur.akkacluster.packageForDialog.PackPushUser;
import org.maxur.akkacluster.packageForDialog.PackUpdateClient;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;

public class ClientActor extends UntypedAbstractActor {	
	
	private Builder builder;
	private ActorRef worker;
	private IUser user;
	
	private int job_id_counter = 0;
	
	@Override
	public void preStart() {
		builder = new Builder(StateUser.REGISTERED);
		builder.build();
		user = builder.getUser();
		
		worker = getContext().actorOf(Props.create(PrimeWorkerActor.class), "worker");
	}
	
	@Override
	public void onReceive(Object message) {
		//сообщение отправлено 
		if (message instanceof PackUpdateClient) {	
			PackUpdateClient packUpdateClient = (PackUpdateClient)message;
			worker.tell(packUpdateClient, getSelf());		
		}
		
		if (message instanceof PackPushRecord) {	
			PackPushRecord packPushRecord = (PackPushRecord)message;
			worker.tell(packPushRecord, getSelf());		
		}
		
		if (message instanceof PackPopRecord) {	
			PackPopRecord packPopRecord = (PackPopRecord)message;
			worker.tell(packPopRecord, getSelf());		
		}
		
		if (message instanceof PackChangeRecord) {
			final PackChangeRecord packChangeRecord = (PackChangeRecord)message;
			worker.tell(packChangeRecord, getSelf());
		}
		
		if (message instanceof PackPushUser) {
			final PackPushUser packPushUser = (PackPushUser)message;
			worker.tell(packPushUser, getSelf());
		}
		
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
	
}

