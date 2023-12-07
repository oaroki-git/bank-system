package bank;
import bank.user.*;
import bank.IO;
import java.util.ArrayList;

public class Main {
	
    static boolean proceed = true;
    static Collection accounts = new Collection();
    static int choice = 0;
    static char page = 'l';
    static Account account = null;
    static String str = "";
    static String[] split = null;

    //mainloop
    public static void main(String[] args) throws Exception{
	
	create("admin", "password");
	IO.print("welcome to your definetly safe and real neighbour bank");
    	
	while (proceed) {
	    switch(page){
		case 'l': loginPage(); break;
		case 'm': managerPage(account); break;
		case 'u': userPage(account); break;
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

    //login
    public static void loginPage()throws Exception{
	try{choice = Integer.parseInt(IO.input("select login \n1. manager   2. user\n"));} 
	catch (NumberFormatException e){
	    IO.print("invalid choice");
	    page = 'l';
	    return;
	}

	if ( choiceCheck(2)){
	    str = IO.input("enter username and password (separated by commas)\n");
	    split = str.split(",");
	    account = get(split[0]);
	    login(account, split[1]);

	    switch(choice){
		case 1: page = 'm'; return;
		case 2: page = 'u'; return;
	    //change after adding the getIdentity funtions(or not)
	    }
	}
    }

    //manager pages
    public static void managerPage(Account account)throws Exception{
	if(account == null){IO.print("error no user passed\n"); page = 'l'; return;}
	if(!(account instanceof Admin)) {IO.print("invalid credentials\n"); page = 'l'; return;}

	Admin manager = (Admin) account;
	IO.print("logged in as manager" + manager.getUsername() + "\n");
	
	try{choice = Integer.parseInt(IO.input("~manager~\n1. view transactions 2. add account 3. change interest rate \npress any key to log out\n"));}
	catch (NumberFormatException e){
	    manager.logout();
	    page = 'l';
	    return;
	}

	switch(choice){
	    case 1: return; //view transactions
	    case 2: addAccountPage(manager); return;
	    case 3: changeInterestPage(manager); return;
	    //case [n]: managerSettingsPage(manager); //for if we need a settings page
	    default: manager.logout(); page = 'l'; return;
	}
	
    }

    public static void changeInterestPage(Admin manager) throws Exception{
	if(manager == null){IO.print("error no user passed\n"); page = 'l'; return;}
	IO.print("this functionality does not exist yet\n");
    };

    public static void addAccountPage(Admin manager) throws Exception{
	if(manager == null){IO.print("error no user passed\n"); page = 'l'; return;}

	try{choice = Integer.parseInt(IO.input("~add account~\n1. user 2. manager \npress any key to go back\n"));}
	catch (NumberFormatException e){return;}

	switch(choice){
	    case 1:
		str = IO.input("enter username, password and adress of new user (separated by commas)\n");
		split = str.split(",");
		create(split[0], split[1], manager, split[2]);
		IO.print("user " + split[0] + " created");
		return;
	    case 2:
	        str = IO.input("enter username and password of new manager (separated by commas)\n");
    		split = str.split(",");
		create(split[0], split[1], manager);
		IO.print("manager " + split[0] + " created");
		return;
	    default: return;
	}
    }
    
    //manager doesn't need a settings page (i thought it did and idk if we'll add one so here is the leftover code)
    /*
    public static void managerSettingsPage(Admin manager){
	if(manager == null){IO.print("error no user passed\n"); return;}

	try{choice = Integer.parseInt(IO.input("~manager settings~\n1. change password 2. change address \n press any key to go back\n"));}
	catch (NumberFormatException e){
	    IO.print("invalid choice\n");
	    page = 's';
	    return;
	}

    }
    */

    //user pages
    public static void userPage(Account account)throws Exception{
	if(account == null){IO.print("error no user passed\n"); page = 'l'; return;}
	if(!(account instanceof User)) {IO.print("invalid credentials\n"); page = 'l'; return;}
	
	User user = (User) account;
	IO.print("logged in as user " + user.getUsername() + "\n");

	try{choice = Integer.parseInt(IO.input("~user~\n1. deposit 2. withdraw 3. settings\npress any key to log out\n"));}
	catch (NumberFormatException e){
	    user.logout();
	    page = 'l';
	    return;
	}

	switch(choice){
	    case 1: return; //deposit
	    case 2: return; //withdraw
	    case 3: userSettingsPage(user); return;
	    default: user.logout(); page = 'l'; return;
	}
    }

    public static void userSettingsPage(User user)throws Exception{
	if(user == null){IO.print("error no user passed\n"); page = 'l'; return;}

	try{choice = Integer.parseInt(IO.input("~user settings~\n1. change password 2. change address 3. add card\n press any key to go back\n"));}
	catch (NumberFormatException e){return;}

	switch(choice){
	    case 1: 
		user.setPassword(IO.input("enter your old password"), IO.input("enter your new password"));
	    case 2:
		user.setAddress(IO.input("enter your password again"), IO.input("enter your new address"));
	    case 3:
		user.addCard(IO.input("enter your password again"));
		ArrayList<Integer> ids = user.getIDs();
		IO.print("your id for this card is " + ids.get(ids.size()-1));

	    default: return;

	}
    }

    //quality of life
    //this pretty much became useless anyway but i'm too lazy to delete
    //maybe someone else do it for me thx <3
    //it's literally only used once
    public static boolean choiceCheck(int choices){return (choice < choices+1 && choice > 0);}
}
