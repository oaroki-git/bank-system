package bank;
import bank.user.*;

import java.util.Scanner;
import java.util.ArrayList;

public class Main {
	
    static boolean proceed = true;
    static Scanner s = new Scanner(System.in);
    Collection accounts = new Collection();
    int choice = 0;
    int page = 'l';

    create("admin", "password");
    public static void main(String[] args) throws Exception{

    	while (proceed) {
	    switch(page){
		case 'l': loginPage();
		case 'm': managerPage();
		case 'u': userPage();
	    }
    	}
    }

    //all create accounts

    public static void create(String username, String password) throws Exception{
    	accounts.add(new Admin(username, password));}

    public static void create(String username, String password, Admin admin) throws Exception{
    	accounts.add(new Admin(username, password, admin));}

    public static void create(String username, String password, Admin admin, String address)throws Exception{
    	accounts.add(new User(username, password, address, admin));}

   //login to account

    public static Account get(String username){return accounts.get(username);} //get account and pass to login

    public static void login(Account account, String password) throws Exception{account.login(password);}

    //interfaces

    public static void loginPage(){
	System.out.println("select login \n1. manager   2. user");
	choice = s.nextInt();

	System.out.println("enter username and password (separated by space)");
	String str = s.nextLine();
	String[] splited = str.split("\\s+");
	login(get(splited[0]), splited[1]);

	switch(choice){
	    case 1: page = 'm';
	    case 2: page = 'u';

	}
    }

    public static void managerPage(){
	System.out.println("~manager~");
    }

    public static void userPage(){
	System.out.println("~user~");
    }
}
