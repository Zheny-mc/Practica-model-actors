package org.maxur.akkacluster;

import org.maxur.akkacluster.RegistrationUser.Builder;
import org.maxur.akkacluster.RegistrationUser.StateUser;

public class RegistrationNewUser {
	
	public static void main(String[] args) {
		Builder builder = new Builder(StateUser.UNREGISTERED);

		builder.build();
		
		
	}
	
}
