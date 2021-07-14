package org.maxur.akkacluster.senderMiroService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import javax.activation.DataHandler;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.maxur.akkacluster.Users.IUser;
import org.maxur.akkacluster.Users.RegisteredUser;
import org.maxur.akkacluster.Users.UserMail;
import org.maxur.akkacluster.baseData.Record;
import org.maxur.akkacluster.baseData.SQLdataBaseActor;

import akka.actor.ActorRef;
import akka.actor.Props;

public class Email extends IMessager {
	
	private ActorRef repository = getContext().actorOf(Props.create(SQLdataBaseActor.class), "repository");
	
	public Email() {
		name = "Email";
		users = new ArrayList<IUser>();
		users.add(new UserMail("Евгений", "Бубнов", "ngtu.19ivt2@yandex.ru", "19-ivt-2IRIT"));
		users.add(new UserMail("Вова", "Смолков", "ngtu.19ivt2@yandex.ru", "19-ivt-2IRIT"));
	}
	
	public void send(final String senderLogin, final String senderPasword, 
					final String selfLogin, List<Record> records) throws AddressException, MessagingException {
		//Объект properties хранит параметры соединения.
        //Для каждого почтового сервера они разные.
        //Если не знаете нужные - обратитесь к администратору почтового сервера.
        //Ну или гуглите;=)
        //Конкретно для Yandex параметры соединения можно подсмотреть тут: 
        //https://yandex.ru/support/mail/mail-clients.html (раздел "Исходящая почта")
        Properties properties = new Properties();
        //Хост или IP-адрес почтового сервера
        properties.put("mail.smtp.host", "smtp.yandex.ru");
        //Требуется ли аутентификация для отправки сообщения
        properties.put("mail.smtp.auth", "true");
        //Порт для установки соединения
        properties.put("mail.smtp.socketFactory.port", "465");
        //Фабрика сокетов, так как при отправке сообщения Yandex требует SSL-соединения
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        
    	//Создаем соединение для отправки почтового сообщения
        Session session = Session.getDefaultInstance(properties,
                //Аутентификатор - объект, который передает логин и пароль
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(senderLogin, senderPasword);
                    }
                });
    	
    	//Создаем новое почтовое сообщение
        Message message = new MimeMessage(session);
        //От кого
        message.setFrom(new InternetAddress(senderLogin));
        //Кому
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(selfLogin));
        //Тема письма
        message.setSubject("Очень важное письмо!!!");
        //текст письма
        String text = "";
        for (int i = 0; i < records.size(); ++i)
        	text += i + ") "+ records.get(i).getName() + " " + 
        					  records.get(i).getTopic() + " " + 
        					  records.get(i).getAudienceNumber() + "\n";
        message.setText(text);
	    //Поехали!!!
	    Transport.send(message);
	}
	
	@Override
	public void sendAll() {
		for (IUser i: users) {
			System.out.println("Отправлено: " + i.getRecords().size() + " записи\n" + 
					"от:" + name + "\n" +  "к: "+ i.getName() + " " + i.getSurname() +"\n");
			
			List<Record> records = new ArrayList<Record>(i.getRecords().values());
			
			try {
				send(i.getLogin(), i.getPassword(), i.getLogin(), records);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onReceive(Object message) {
		if (message instanceof MailMessage) {
			repository.tell(message, getSelf());
		}
		
		if (message instanceof Map<?, ?>) {			
			final Map<Integer, Record> records = (Map<Integer, Record>)message;
			
			Iterator it = records.entrySet().iterator();
			
			while (it.hasNext()) {
				Entry entry = (Entry) it.next();
				
				for (IUser j: users) {
					Integer key = (Integer) entry.getKey();
					Record value = (Record) entry.getValue();
					j.pushRecord(key, value);
				}
			}
		
			sendAll();		
		}
		
		unhandled(message);
		
	}
	
}
