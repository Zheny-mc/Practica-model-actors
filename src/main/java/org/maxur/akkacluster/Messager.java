package org.maxur.akkacluster;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;

/**
 * @author Maxim Yunusov
 * @version 1.0 14.09.2014
 */
public class Messager extends UntypedAbstractActor {
    
	private ActorRef repository = getContext().actorOf(Props.create(Repository.class), "repository");
	
	public void send(Record response) throws InterruptedException {
        // Имитация бурной деятельности
        Thread.sleep(100);
    }

    public void done() {
        // TODO
    }

	@Override
	public void onReceive(Object message) throws Throwable {
		if (message instanceof MailMessage) {
			repository.tell(message, getSelf());
		}
		
		if (message instanceof Record) {			
			final Record record = new Record((Record) message);
			send(record);
			System.out.println("Запрос на обновление");		
		}
		
		unhandled(message);
	}
}
