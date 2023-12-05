package bank;
import bank.user.*;
import bank.IO;

public class Main {
	
    static boolean proceed = true;
    static Collection accounts = new Collection();
    static int choice = 0;
    static int page = 'l';

    //mainloop
    public static void main(String[] args) throws Exception{
	
	create("admin", "password");
    	
	while (proceed) {
	    switch(page){
		case 'l': loginPage(); break;
		case 'm': managerPage(); break;
		case 'u': userPage(); break;
	    }
    	}
    }

    //create accounts

    public static void create(String username, String password) throws Exception{
    	accounts.add(new Admin(username, password));}

    public static void create(String username, String password, Admin admin) throws Exception{
    	accounts.add(new Admin(username, password, admin));}

    public static void create(String username, String password, Admin admin, String address)throws Exception{
    	accounts.add(new User(username, password, address, admin));}

   //login to account

    public static Account get(String username)throws Exception{return accounts.get(username);} //get account and pass to login
    public static void login(Account account, String password)throws Exception{account.login(password);}

    //interfaces

    public static void loginPage()throws Exception{
	try{
	    choice = Integer.parseInt(IO.input("select login \n1. manager   2. user\n"));
	} catch (NumberFormatException e){
	    IO.print("invalid choice");
	    page = 'l';
	    return;
	}

	String str = IO.input("enter username and password (separated by commas)\n");
	String[] split = str.split(",");
	login(get(split[0]), split[1]);

	switch(choice){
	    case 1: page = 'm';
	    case 2: page = 'u';

	}
    }

    public static void managerPage()throws Exception{
	System.out.println("~manager~");
    }

    public static void userPage()throws Exception{
	System.out.println("~user~");
    }
}
