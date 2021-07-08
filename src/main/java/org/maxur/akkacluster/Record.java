package org.maxur.akkacluster;

public class Record {
	private String topic; //тема
	private Integer audienceNumber; //номер аудитории
	
	public Record() {}

	public Record(String topic, Integer audienceNumber) {
		super();
		this.topic = topic;
		this.audienceNumber = audienceNumber;
	}

	public Record(Record obj) {
		this.topic = obj.getTopic();
		this.audienceNumber = obj.getAudienceNumber();
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public Integer getAudienceNumber() {
		return audienceNumber;
	}

	public void setAudienceNumber(Integer audienceNumber) {
		this.audienceNumber = audienceNumber;
	}

	@Override
	public String toString() {
		return "Record [topic=" + topic + ", audienceNumber=" + audienceNumber + "]";
	}
	
	
	
}
