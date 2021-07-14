package org.maxur.akkacluster.Users;

public class InfoUser {
	private final String name;
	private final String surName;
	
	public static InfoUser create(String name, String surName) {
		return new InfoUser(name, surName);
	}
	
	public static InfoUser create(IUser user) {
		return new InfoUser(user);
	}
	
	public InfoUser(IUser user) {
		super();
		this.name = user.getName();
		this.surName = user.getSurname();
	}
	
	public InfoUser(String name, String surName) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((surName == null) ? 0 : surName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InfoUser other = (InfoUser) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (surName == null) {
			if (other.surName != null)
				return false;
		} else if (!surName.equals(other.surName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "InfoUser [name=" + name + ", surName=" + surName + "]";
	}
	
}
