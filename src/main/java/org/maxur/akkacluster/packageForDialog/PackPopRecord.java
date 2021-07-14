package org.maxur.akkacluster.packageForDialog;

import org.maxur.akkacluster.Users.InfoUser;

public class PackPopRecord {
	private final Integer id;
	private final InfoUser infoUser;
	
	public static PackPopRecord create(Integer id, InfoUser infoUser) {
		return new PackPopRecord(id, infoUser);
	}
	
	public PackPopRecord(Integer id, InfoUser infoUser) {
		super();
		this.id = id;
		this.infoUser = infoUser;
	}

	public Integer getId() {
		return id;
	}

	public InfoUser getInfoUser() {
		return infoUser;
	}

}
