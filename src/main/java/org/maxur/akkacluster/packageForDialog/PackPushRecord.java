package org.maxur.akkacluster.packageForDialog;

import org.maxur.akkacluster.baseData.Record;

public class PackPushRecord {
	private final Integer id;
	private final Record record;
	
	public static PackPushRecord create(Integer id, Record record) {
		return new PackPushRecord(id, record);
	}
	
	public PackPushRecord(Integer id, Record record) {
		super();
		this.id = id;
		this.record = record;
	}

	public Integer getId() {
		return id;
	}

	public Record getRecord() {
		return record;
	}
	
	
}
