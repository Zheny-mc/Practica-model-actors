package org.maxur.akkacluster;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;

public class Client extends UntypedAbstractActor {		
	
	private ActorRef worker = getContext().actorOf(Props.create(PrimeWorker.class), "worker");
	
	@Override
	public void onReceive(Object arg0) {
		
		if (arg0 instanceof Record) {			
			final Record record = new Record((Record) arg0);
			worker.tell(record, getSelf());		
		}
		
		if (arg0 instanceof String) {
			final String ans = (String) arg0;			
			System.out.println("Операция выполнена: " + ans);
		}
		
		unhandled(arg0);		
	}
	
}

