package bank;
import bank.user.*;

import java.util.Scanner;
import java.util.ArrayList;

public class Main {
	
	static boolean proceed = true;
	static Scanner s = new Scanner(System.in);
	static ArrayList<Account> accounts = new ArrayList<Account>();

	public static void main(String[] args) throws Exception{
		print("enter Admin username");
		String tempName = s.nextLine();
		print("enter Admin password");
		String tempPass = s.nextLine();


		create(tempName, tempPass);

		while (proceed) {
			continue;	
		}
	}

	public static void create(String username, String password) throws Exception{
		accounts.add(new Admin(username, password));
	}

	/*
	public static void create(String username, String password, Admin admin) throws Exception{
		accounts.add(new Manager(username, password, admin));
	}*/

	public static void create(String username, String password, Admin admin, String address)throws Exception{
		accounts.add(new User(username, password, address, admin));
	}


	public static void print(String text, boolean newline){
		if (newline){System.out.println(text);}
		else{System.out.print(text);}
	}
	public static void print(String text){
		System.out.println(text);
	}

}