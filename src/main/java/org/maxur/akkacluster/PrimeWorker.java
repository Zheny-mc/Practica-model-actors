package org.maxur.akkacluster;

import static akka.actor.ActorRef.noSender;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;

public class PrimeWorker extends UntypedAbstractActor {

	private ActorRef repository = getContext().actorOf(Props.create(Repository.class), "repository");
	private ActorRef client = getContext().actorOf(Props.create(Client.class), "client");
	
	private int job_id_counter = 0;
	
	@Override
	public void onReceive(Object message) {
		if (message instanceof Record) {
			final Record record = new Record((Record) message);
			repository.tell(record, getSelf());
		}
		
		if (message instanceof String) {
			job_id_counter++;
			final String ans = (String) message + job_id_counter;
			//System.out.println(ans);
			client.tell(ans, getSelf());
		}
		
		
		
		unhandled(message);		
	}

}



