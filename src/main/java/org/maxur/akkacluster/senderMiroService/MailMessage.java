package org.maxur.akkacluster.senderMiroService;

public class MailMessage {
	private final String message;
	
	public MailMessage(String message) {
		super();
		this.message = message;
	}
	
	public static MailMessage create(String message) {
		return new MailMessage(message);
	}
	
	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return message;
	}
	
	
}
