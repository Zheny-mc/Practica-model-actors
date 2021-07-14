package org.maxur.akkacluster.packageForDialog;

import org.maxur.akkacluster.baseData.Record;

public class PackChangeRecord {
	private final Integer oldId;
	private final Integer newId;
	private final Record record;
	
	public static PackChangeRecord create(Integer oldId, Integer newId, Record record) {
		return new PackChangeRecord(oldId, newId, record);
	}
	
	public PackChangeRecord(Integer oldId, Integer newId, Record record) {
		super();
		this.oldId = oldId;
		this.newId = newId;
		this.record = record;
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
	
}
