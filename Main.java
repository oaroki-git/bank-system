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
	IO.print("welcome to your definetly safe and real neighbour bank\n");
    	
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
    public static Account get(String username)throws Exception{
	Account acc = accounts.get(username);
	if (acc==null) {
	throw new Exception("invalid username");}
	return acc;} //get account and pass to login
    public static int login(Account account, String password)throws Exception{return account.login(password);}

    
    //interfaces ---------------------------------------------------------------------------------


    //login
    public static void loginPage()throws Exception{
	try{choice = Integer.parseInt(IO.input("select login \n1. manager   2. user\n"));} 
	catch (NumberFormatException e){
	    IO.print("invalid choice");
	    page = 'l';
	    return;
	}

	if (choice > 0 && choice < 3){
	    str = IO.input("enter username and password (separated by commas)\n");
	    split = str.split(",");
	    try{account = get(split[0]);}
	    catch (Exception e){IO.print("invalid username or password\n"); page = 'l'; return;}
	    if (login(account, split[1]) == 1){IO.print("invalid username or password\n"); page = 'l'; return;}

	    switch(choice){
		case 1: page = 'm'; return;
		case 2: page = 'u'; return;
	    }
	}
    }


    //manager pages
    public static void managerPage(Account account)throws Exception{

	if(account == null){IO.print("error no user passed\n"); page = 'l'; return;}
	if(!(account instanceof Admin)) {IO.print("invalid credentials\n"); page = 'l'; return;}

	Admin manager = (Admin) account;
	IO.print("logged in as manager " + manager.getUsername() + "\n");
	
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
		try{ create(split[0], split[1], manager, split[2]);}
		catch (Exception e){
		    if(e instanceof IndexOutOfBoundsException){IO.print("please fill in all fields."); return;}
		    IO.print("This user already exists. please make sure the username and password are unique.\n"); return;
		}
		IO.print("user " + split[0] + " created \n");
		return;
	    case 2:
	        str = IO.input("enter username and password of new manager (separated by commas)\n");
    		split = str.split(",");
		try{ create(split[0], split[1], manager);}
		catch (Exception e){
		    if(e instanceof IndexOutOfBoundsException){IO.print("please fill in all fields."); return;}
		    IO.print("This account already exists. please make sure the username and password are unique.\n"); return;
		}
		IO.print("manager " + split[0] + " created \n");
		return;
	    default: return;
	}
    }
    

    //user pages
    public static void userPage(Account account)throws Exception{
	if(account == null){IO.print("error no user passed\n"); page = 'l'; return;}
	if(!(account instanceof User)) {IO.print("invalid credentials\n"); page = 'l'; return;}
	
	User user = (User) account;
	IO.print("logged in as user " + user.getUsername() + "\n");
	if(user.getCards().size() == 0){IO.print("you currently don't have a bank card. add one in settings.\n")}

	Card card = null;
	int amount = 0;

	try{choice = Integer.parseInt(IO.input("~user~\n1. deposit 2. withdraw 3. settings\npress any key to log out\n"));}
	catch (NumberFormatException e){
	    user.logout();
	    page = 'l';
	    return;
	}

	switch(choice){
	    case 1: deposit(user, amount, card) return;
	    case 2: withdraw(user, amount, card); return;
	    case 3: userSettingsPage(user); return;
	    default: user.logout(); page = 'l'; return;
	}
    }


    public static void deposit(User user, int amount, Card card)throws Exception{
	if(user.getCards().size() == 0){IO.print("you don't have a bank card. create one in settings and try again.\n"); return;}

	card = chooseCard(user);
	if(card == null){IO.print("invalid card."); return;}

	try{amount = Integer.parseInt(IO.input("enter the amount you want to deposit:\n"));}
	catch (NumberFormatException e){IO.print("please enter a valid amount\n");}

	if(!(card.deposit(amount, IO.input("enter your password again\n")) == 0)){IO.print("please make sure the amount and password are valid");};
	IO.print("your balance was $" + (card.getBalance() - amount) + "and is now $" + card.getBalance());
    }

    public static void withdraw(User user, int amount, Card card)throws Exception{
	if(user.getCards().size() == 0){IO.print("you don't have a bank card. create one in settings and try again.\n"); return;}
	    
	card = chooseCard(user);
	if(card ==null){IO.print("invalid card."); return;}

	try{amount = Integer.parseInt(IO.input("enter the amount you want to withdraw:\n"));}
	catch (NumberFormatException e){IO.print("please enter a valid amount\n");}
	if(!(card.withdraw(amount, IO.input("enter your password again\n")) == 0)){IO.print("please make sure the amount and password are valid");};
	IO.print("your balance was $" + (card.getBalance() + amount) + "and is now $" + card.getBalance());  
    }

    public static void userSettingsPage(User user)throws Exception{
	if(account.getStatus()){IO.print("user logged in");}
	else{IO.print("user logged out");}

	if(user == null){IO.print("error no user passed\n"); page = 'l'; return;}

	try{choice = Integer.parseInt(IO.input("~user settings~\n1. change password 2. change address 3. add card\n press any key to go back\n"));}
	catch (NumberFormatException e){return;}

	switch(choice){
	    case 1: 
		user.setPassword(IO.input("enter your old password"), IO.input("enter your new password")); return;
	    case 2:
		user.setAddress(IO.input("enter your password again"), IO.input("enter your new address")); return;
	    case 3:
		user.addCard(IO.input("enter your password again"));
		ArrayList<Integer> ids = user.getIDs();
		IO.print("your id for this card is " + ids.get(ids.size()-1));
		return;

	    default: return;

	}
    }


    public static Card chooseCard(User user)throws Exception{
	IO.print("choose card to use below: \n");
	ArrayList<Card> cards = user.getCards();
	int i = 1;

	IO.print("No. | ID	    | balance\n");
	for(Card card : cards){
	    IO.print(i + ". | " + card.getID() + " | " + card.getBalance() +"\n");
	    i += 1;
	}
	try{choice = Integer.parseInt(IO.input("enter a number:\n"));}
	catch (NumberFormatException e){IO.print("invalid option"); return null; );}
	try{return cards.get(choice-1);}
	catch (IndexOutOfBoundsException e){IO.print("invalid option"); return null;}
    }
}
