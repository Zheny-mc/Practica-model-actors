package org.maxur.akkacluster.packageForDialog;

public class PackPushUser {
	final private String name;
	final private String surName;
	
	public static PackPushUser create(String name, String surName) {
		return new PackPushUser(name, surName);
	}
	
	public PackPushUser(String name, String surName) {
		super();
		this.name = name;
		this.surName = surName;
	}

	public String getName() {
		return name;
	}

	public String getSurName() {
		return surName;
	}
	
	
}
