package org.maxur.akkacluster.packageForDialog;

import org.maxur.akkacluster.Users.InfoUser;
import org.maxur.akkacluster.baseData.Record;

public class PackPushRecord {
	private final Integer id;
	private final Record record;
	private final InfoUser infoUser;
	
	public static PackPushRecord create(Integer id, Record record, InfoUser infoUser) {
		return new PackPushRecord(id, record, infoUser);
	}
	
	public PackPushRecord(Integer id, Record record, InfoUser infoUser) {
		super();
		this.id = id;
		this.record = record;
		this.infoUser = infoUser;
	}

	public Integer getId() {
		return id;
	}

	public Record getRecord() {
		return record;
	}
	
	public InfoUser getInfoUser() {
		return infoUser;
	}
	
	
}
