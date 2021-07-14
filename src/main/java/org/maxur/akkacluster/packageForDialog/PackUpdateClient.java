package org.maxur.akkacluster.packageForDialog;

import org.maxur.akkacluster.Users.InfoUser;

public class PackUpdateClient {
	private final InfoUser infoUser;

	public static PackUpdateClient create(InfoUser infoUser) {
		return new PackUpdateClient(infoUser);
	}

	public PackUpdateClient(InfoUser infoUser) {
		super();
		this.infoUser = infoUser;
	}
	
	public InfoUser getInfoUser() {
		return infoUser;
	}
	
	public String getName() {
		return infoUser.getName();
	}

	public String getSurName() {
		return infoUser.getSurName();
	}
	
}
