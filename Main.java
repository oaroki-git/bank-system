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
	IO.print("welcome to your definitely safe and real neighbour bank\n");
    	
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
	return acc;
	} //get account and pass to login
    public static int login(Account account, String password)throws Exception{
	if (account==null) {throw new Exception("invalid username");}
	return account.login(password);
    }

    
    //interfaces ---------------------------------------------------------------------------------


    //login
    public static void loginPage()throws Exception{
	try{choice = Integer.parseInt(IO.input("select login \n1. manager   2. user\n"));} 
	catch (NumberFormatException e){
	    IO.print("invalid choice\n");
	    page = 'l';
	    return;
	}

	if (choice > 0 && choice < 3){
	    str = IO.input("enter username and password (separated by commas)\n");
	    split = str.split(",");
	    try{account = get(split[0]);}
	    catch (Exception e){System.out.println(e);}//IO.print("index out of bounds");}IO.print("invalid username or password\n"); page = 'l'; return;}
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
	IO.print("\n---------------------\nlogged in as manager " + manager.getUsername() + "\n");
	
	try{choice = Integer.parseInt(IO.input("\n~actions~\n1. view transactions 2. add account 3. interest rate \npress any key to log out\n"));}
	catch (NumberFormatException e){
	    manager.logout();
	    page = 'l';
	    return;
	}

	switch(choice){
	    case 1: viewTransactions(manager); return;
	    case 2: addAccountPage(manager); return;
	    case 3: interestPage(manager); return;
	    default: manager.logout(); page = 'l'; return;
	}
	
    }
    
    public static void viewTransactions(Admin manager) throws Exception{
	if(manager == null){IO.print("error no user passed\n"); return;}
	ArrayList<Transanction> transactions = Card.getAllTransanctions(manager);
	IO.print("\n~transactions~\n");
	IO.print("time | card id | amount\n");
	for(Transanction t : transactions){
	    IO.print(t + "\n");
	}
    }

    public static void interestPage(Admin manager) throws Exception{
	if(manager == null){IO.print("error no user passed\n"); return;}
	
	Card card = chooseCard(manager);
	if(card == null){return;}

	try{choice = Integer.parseInt(IO.input("~interest rates~\n1. apply interest 2. set interest\n"));}
	catch(NumberFormatException e){IO.print("please enter a valid choice.\n"); return;}

	switch(choice){
	    case 1: card.applyInterest(manager); return;
	    case 2: card.setInterestRate(((double)(Integer.parseInt(IO.input("enter the new interest rate for this card(must be a double): "))))/100, manager);
		    IO.print("the interest rate is now" + card.getInterestRate()*100 + "%.\n");
		    return;
	    default: return;
	}
    };

    public static void addAccountPage(Admin manager) throws Exception{
	if(manager == null){IO.print("error no user passed\n"); return;}

	try{choice = Integer.parseInt(IO.input("\n~add account~\n1. user 2. manager \npress any key to go back\n"));}
	catch (NumberFormatException e){return;}

	switch(choice){
	    case 1:
		str = IO.input("enter username, password and address of new user (separated by commas)\n");
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
	IO.print("\n---------------------\nlogged in as user " + user.getUsername() + "\n");
	if(user.getIDs().size() == 0){IO.print("\n!! you currently don't have a bank card. add one in settings. !!\n");}

	Card card = null;
	double amount = 0;

	try{choice = Integer.parseInt(IO.input("\n~actions~\n1. deposit 2. withdraw 3. view transaction history 4. settings\npress any key to log out\n"));}
	catch (NumberFormatException e){
	    user.logout();
	    page = 'l';
	    return;
	}

	switch(choice){
	    case 1: deposit(user, amount, card); return;
	    case 2: withdraw(user, amount, card); return;
	    case 3: viewTransactions(user); return;
	    case 4: userSettingsPage(user); return;
	    default: user.logout(); page = 'l'; return;
	}
    }

     public static void viewTransactions(User user) throws Exception{
	    if(user == null){IO.print("error no user passed\n"); return;}

	    Card card = chooseCard(user);
	    ArrayList<Transanction> transactions = card.getTransanctions();
	    IO.print("time, card id, amount\n");
	    for(Transanction t : transactions){
		IO.print(t + "\n");
	    }
	}


    public static void deposit(User user, double amount, Card card)throws Exception{
	if(user.getIDs().size() == 0){IO.print("you don't have a bank card. create one in settings and try again.\n\n"); return;}

	card = chooseCard(user);
	if(card == null){IO.print("invalid card. \nplease verify that a valid card was chosen and a valid password input.\n\n"); return;}

	try{amount = Double.parseDouble(IO.input("enter the amount you want to deposit:\n"));}
	catch (NumberFormatException e){IO.print("please enter a valid amount\n"); return;}

	if(!(card.deposit(amount) == 0)){IO.print("please make sure the amount and password are valid\n\n"); return;}
	IO.print("your balance was $" + (card.getBalance() - amount) + " and is now $" + card.getBalance() + "\n");
    }

    public static void withdraw(User user, double amount, Card card)throws Exception{
	if(user.getIDs().size() == 0){IO.print("you don't have a bank card. create one in settings and try again.\n\n"); return;}
	    
	card = chooseCard(user);
	if(card ==null){IO.print("invalid card."); return;}

	try{amount = Double.parseDouble(IO.input("enter the amount you want to withdraw:\n"));}
	catch (NumberFormatException e){IO.print("please enter a valid amount\n"); return;}

	if(!(card.withdraw(amount) == 0)){IO.print("please make sure the amount and password are valid\n\n"); return;};
	IO.print("your balance was $" + (card.getBalance() + amount) + " and is now $" + card.getBalance() + "\n");  
    }

    public static void userSettingsPage(User user)throws Exception{
	if(account.getStatus()){IO.print("\n---------------------\nlogged in as user " + user.getUsername() + "\n");}
	else{IO.print("user logged out");}

	if(user == null){IO.print("error no user passed\n"); return;}

	try{choice = Integer.parseInt(IO.input("\n~user settings~\n1. change password 2. change address 3. add card\n press any key to go back\n"));}
	catch (NumberFormatException e){return;}

	switch(choice){
	    case 1: 
		user.setPassword(IO.input("enter your new password ")); return;
	    case 2:
		user.setAddress(IO.input("enter your new address ")); return;
	    case 3:
		user.addCard(IO.input("enter a password for you card. \nnote that it is IMMUTABLE. please choose wisely "));
		ArrayList<Integer> ids = user.getIDs();
		IO.print("your id for this card is " + ids.get(ids.size()-1) + "\n");
		return;

	    default: return;

	}
    }


    public static Card chooseCard(User user)throws Exception{

	ArrayList<Integer> ids = user.getIDs();
	Card card = null;
	if(ids.size() == 0){IO.print("you currently don't have any cards. add one and try again.\n\n"); return null;}

	IO.print("choose card to use below: \n");
	int i = 1;

	IO.print("card number, ID, balance\n");
	for(int id : ids){
	    card = user.getCard(id);
	    IO.print(i + ". | " + id + " | " + card.getBalance() +"\n");
	    i += 1;
	}
	try{choice = Integer.parseInt(IO.input("enter a number:\n"));}
	catch (NumberFormatException e){IO.print("invalid option\n\n"); return null; }
	try{
	    card = user.getCard(ids.get(choice - 1));
	    if(card.login(IO.input("enter the card password: ")) == 0){return card;}
	    else{IO.print("wrong card password.\n\n"); return null;}
	}

	catch (IndexOutOfBoundsException e){IO.print("invalid option\n\n"); return null;}
    }

    public static Card chooseCard(Admin manager)throws Exception{

	ArrayList<String[]> ids = User.getAllIDs(manager);
	Card card = null;
	if(ids.size() == 0){IO.print("\nthis system currently has no cards. \n"); return null;}
	
	IO.print("choose card to use below: \n");
	int i = 1;

	IO.print("card number, ID, owner, balance\n");
	for(String[] pair : ids){
	    IO.print(i + ". | " + pair[1] + " | " + pair[0] + " | " + User.getCardGlobal(Integer.parseInt(pair[1]), manager).getBalance() +"\n");
	    i += 1;
	}
	try{choice = Integer.parseInt(IO.input("enter a number:\n"));}
	catch (NumberFormatException e){IO.print("invalid option\n\n"); return null; }
	try{
	    card = User.getCardGlobal(Integer.parseInt(ids.get(choice - 1)[1]), manager);
	    return card;
	}

	catch (IndexOutOfBoundsException e){return null;}


    }
}
