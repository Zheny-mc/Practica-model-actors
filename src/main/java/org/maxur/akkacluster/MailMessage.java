package org.maxur.akkacluster;

public class MailMessage {
	private String message;

	public MailMessage() {}
	
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

	public void setMessage(String message) {
		this.message = message;
	}
}
