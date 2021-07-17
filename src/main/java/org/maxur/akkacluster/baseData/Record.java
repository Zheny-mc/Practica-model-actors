package org.maxur.akkacluster.baseData;

public class Record {
	private String name; //имя
	private String topic; //тема
	private Integer audienceNumber; //номер аудитории
	
	public Record() {}

	public Record(String name, String topic, Integer audienceNumber) {
		super();
		this.name = name;
		this.topic = topic;
		this.audienceNumber = audienceNumber;
	}

	public Record(Record obj) {
		this.name = obj.getName();
		this.topic = obj.getTopic();
		this.audienceNumber = obj.getAudienceNumber();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((audienceNumber == null) ? 0 : audienceNumber.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((topic == null) ? 0 : topic.hashCode());
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
		Record other = (Record) obj;
		if (audienceNumber == null) {
			if (other.audienceNumber != null)
				return false;
		} else if (!audienceNumber.equals(other.audienceNumber))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (topic == null) {
			if (other.topic != null)
				return false;
		} else if (!topic.equals(other.topic))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Record [name=" + name + ", topic=" + topic + ", audienceNumber=" + audienceNumber + "]";
	}
	
	
	
}
