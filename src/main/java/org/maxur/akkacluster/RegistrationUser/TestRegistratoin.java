package org.maxur.akkacluster.RegistrationUser;

public class TestRegistratoin {
	
	public static void main(String[] args) {
		
		Builder builder = new Builder(StateUser.REGISTERED);
		
		builder.build();
		
		builder.getUser();
    }
	
	/*
	//int sundayIndex = StateUser.ADMIN.ordinal();
	//System.out.println(sundayIndex);
	
	public static void testRegisration() {
		Regisration reg = Regisration.create("Evgeny", "Bubnov", "hello", "hello");
		if (reg.getUser() != null)
			System.out.println("Пользователь зарегестрирован");
		else
			System.out.println("Ошибка!!!");
	}
	
	public static void testEntrance() {
		Entrance entr = new Entrance("hello", "hello");
		if (entr.check())
			System.out.println("Вход в систему...");
		else
			System.out.println("Ошибка!!!");
	}
   	*/
	
}

