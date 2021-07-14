package org.maxur.akkacluster.mainMircoService;

import static akka.actor.ActorRef.noSender;

import java.util.Map;

import org.maxur.akkacluster.Users.Client;
import org.maxur.akkacluster.baseData.Record;
import org.maxur.akkacluster.baseData.SQLdataBaseActor;
import org.maxur.akkacluster.packageForDialog.PackChangeRecord;
import org.maxur.akkacluster.packageForDialog.PackPopRecord;
import org.maxur.akkacluster.packageForDialog.PackPushRecord;
import org.maxur.akkacluster.packageForDialog.PackUpdateClient;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;

public class PrimeWorker extends UntypedAbstractActor {

private ActorRef sqlDataBase;
	
	@Override
	public void preStart() {
		sqlDataBase = getContext().actorOf(Props.create(SQLdataBaseActor.class), "sqlDataBase");
	}
	
	@Override
	public void onReceive(Object message) {
		if (message instanceof PackUpdateClient) {	
			final PackUpdateClient packUpdateClient = (PackUpdateClient)message;
			sqlDataBase.tell(packUpdateClient, sender());		
		}
		
		if (message instanceof PackPushRecord) {	
			PackPushRecord packPushRecord = (PackPushRecord)message;
			sqlDataBase.tell(packPushRecord, sender());		
		}
		
		if (message instanceof PackPopRecord) {	
			PackPopRecord packPopRecord = (PackPopRecord)message;
			sqlDataBase.tell(packPopRecord, sender());		
		}
	
		if (message instanceof PackChangeRecord) {
			final PackChangeRecord packChangeRecord = (PackChangeRecord)message;
			sqlDataBase.tell(packChangeRecord, sender());
		}
		
	}

}



