package org.maxur.akkacluster;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;

/**
 * @author Maxim Yunusov
 * @version 1.0 14.09.2014
 */
public class Repository extends UntypedAbstractActor {
	
    public void save(Record response) {
        // Имитация бурной деятельности
        try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //System.out.println("save");
    }

    public void done() {
        // TODO
    }

	@Override
	public void onReceive(Object message) {
		if (message instanceof Record) {
			final Record record = new Record((Record)message);
			save(record);
			getSender().tell("Добавление", getSelf());
		}
		
		if (message instanceof MailMessage) {
			sender().tell(new Record(), getSelf());
		}
		
		unhandled(message);
	}
}
