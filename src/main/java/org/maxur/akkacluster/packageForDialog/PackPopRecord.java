package org.maxur.akkacluster.packageForDialog;

public class PackPopRecord {
	final private Integer id;
	
	public static PackPopRecord create(Integer id) {
		return new PackPopRecord(id);
	}
	
	public PackPopRecord(Integer id) {
		super();
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

}
