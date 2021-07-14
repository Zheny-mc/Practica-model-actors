package org.maxur.akkacluster.baseData;

import java.util.Map;

import org.maxur.akkacluster.Users.IUser;
import org.maxur.akkacluster.Users.InfoUser;

public interface IBaseData {
	public void pushRecord(Integer id, Record record, InfoUser infoUser);
	public void popRecord(Integer id, InfoUser infoUser);
	public void changeRecord(Integer oldId, Integer newId, Record record, InfoUser infoUser);
	public void readBase();
	public void update();
	public Map<InfoUser, IUser> getUsers();
	public Map<Integer, Record> getData();
}
