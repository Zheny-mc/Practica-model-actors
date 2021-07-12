package org.maxur.akkacluster.baseData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.maxur.akkacluster.packageForDialog.PackChangeRecord;
import org.maxur.akkacluster.packageForDialog.PackPopRecord;
import org.maxur.akkacluster.packageForDialog.PackPushRecord;
import org.maxur.akkacluster.packageForDialog.PackUpdateClient;
import org.maxur.akkacluster.senderMiroService.MailMessage;

import akka.actor.UntypedAbstractActor;

import java.util.Scanner;
import java.util.TreeMap;

public class SQLdataBaseActor extends UntypedAbstractActor {
	
	private IBaseData baseData;
	
	@Override
	public void preStart() {
		baseData = SQLdataBase.create();
	}
	
	@Override
	public void onReceive(Object message) throws Exception {
		//update list
		if (message instanceof PackUpdateClient) {	
			final Map<Integer, Record> records = baseData.getData();
			sender().tell(records, getSelf());
		}
		
		if (message instanceof PackPushRecord) {	
			PackPushRecord packPushRecord = (PackPushRecord)message;
			final Integer id = packPushRecord.getId();
			final Record record = packPushRecord.getRecord();
			baseData.pushRecord(id, record);
			sender().tell("добавления: успешно", getSelf());		
		}
		
		if (message instanceof PackPopRecord) {	
			PackPopRecord packPopRecord = (PackPopRecord)message;
			final Integer id = packPopRecord.getId();
			baseData.popRecord(id);
			sender().tell("удаления: успешно", getSelf());	
		}
		
		if (message instanceof PackChangeRecord) {
			final PackChangeRecord packChangeRecord = (PackChangeRecord)message;
			final Integer oldId = packChangeRecord.getOldId();
			final Integer newId = packChangeRecord.getNewId();
			final Record record = packChangeRecord.getRecord();
			baseData.changeRecord(oldId, newId, record);
			sender().tell("изменение: успешно", getSelf());
		}
		
		//------------------------------------------------------------------------
		if (message instanceof MailMessage) {
			final Map<Integer, Record> records = baseData.getData();
			sender().tell(records, getSelf());
		}
		
		unhandled(message);
		
	}

}
