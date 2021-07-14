package org.maxur.akkacluster.packageForDialog;

import org.maxur.akkacluster.Users.InfoUser;
import org.maxur.akkacluster.baseData.Record;

public class PackChangeRecord {
	private final Integer oldId;
	private final Integer newId;
	private final Record record;
	private final InfoUser infoUser;
	
	public static PackChangeRecord create(Integer oldId, Integer newId, Record record, InfoUser infoUser) {
		return new PackChangeRecord(oldId, newId, record, infoUser);
	}
	
	public PackChangeRecord(Integer oldId, Integer newId, Record record, InfoUser infoUser) {
		super();
		this.oldId = oldId;
		this.newId = newId;
		this.record = record;
		this.infoUser = infoUser;
	}

	public Integer getOldId() {
		return oldId;
	}

	public Integer getNewId() {
		return newId;
	}

	public Record getRecord() {
		return record;
	}

	public InfoUser getInfoUser() {
		return infoUser;
	}
	
}
