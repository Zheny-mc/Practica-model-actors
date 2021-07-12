package org.maxur.akkacluster.packageForDialog;

public class PackUpdateClient {
	final private String name;
	final private String surName;
	
	public static PackUpdateClient create(String name, String surName) {
		return new PackUpdateClient(name, surName);
	}

	public PackUpdateClient(String name, String surName) {
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
