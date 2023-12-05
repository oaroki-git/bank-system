package bank;
import bank.user.*;
import bank.IO;

public class Main {
	
    static boolean proceed = true;
    static Collection accounts = new Collection();
    static int choice = 0;
    static int page = 'l';
    static Account account = null;

    //mainloop
    public static void main(String[] args) throws Exception{
	
	create("admin", "password");
    	
	while (proceed) {
	    switch(page){
		case 'l': loginPage(); break;
		case 'm': managerPage(); break;
		case 'u': userPage(); break;
		default: IO.print("page not found");
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
	try{choice = Integer.parseInt(IO.input("select login \n1. manager   2. user\n"));} 
	catch (NumberFormatException e){
	    IO.print("invalid choice");
	    page = 'l';
	    return;
	}

	if ( choiceCheck(2)){
	    String str = IO.input("enter username and password (separated by commas)\n");
	    String[] split = str.split(",");
	    account = get(split[0]);
	    login(account, split[1]);

	    switch(choice){
		case 1: page = 'm'; return;
		case 2: page = 'u'; return;
	    //change after adding the getIdentity funtions
	    }
	}
    }

    public static void managerPage(Account account)throws Exception{
	IO.input("~manager~\n");
    }

    public static void userPage(Account account)throws Exception{
	try{choice = Integer.parseInt(IO.input("~user~\n1. deposit 2. withdraw 3. settings\npress any key to log out\n"));}
	catch (NumberFormatException e){
	    IO.print("invalid choice\n")
	    page = 'l'
	    return;
	}

	switch(choice){
	    case 1:
	    case 2:
	    case 3: page = 's';
	    default: page = 'l'; return;
	}
    }

    public static void userSettingsPage(Account account)throws Exception{
	try{choice = Integer.parseInt(IO.input("~user settings~\n1. change password 2. change address \n press any key to go back\n"));}
	catch (NumberFormatException e){
	    IO.print("invalid choice\n")
	    page = 's'
	    return;
	}

	switch(choice){
	    case 1: 
		account.setPassword(IO.input("enter your old password"), IO.input("enter your new password"));
	    case 2:
		account.setAddress(IO.input("enter your password again"), IO.input("enter your new address"));
	    default: page = 'l'; return;

	}
    }

    //quality of life
    public static boolean choiceCheck(int choices){return (choice < choices+1 && choice > 0);}

