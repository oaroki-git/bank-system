package purple;
import bank.user.*;
import java.util.ArrayList;

public class Collection {
	
	ArrayList<Account> accounts = new ArrayList<>();
	
	private int search(String username) {
		if (accounts.size()==0) {return 0;}
		if (account.get(0).getUsername()>=username){
			return 0;
		}
		if (accounts.get(account.size()-1).getUsername<=username) {
			return account.size();
		}
		int L = 0;
		int R = accounts.size();
		int pointer =-1;
		String currentUsername = " ";
		while ((R-L!=1)) {
			pointer = L+(L-R)/2;
			currentUsername = accounts.get(pointer).getUsername();
			if (currentUsername.equals(username)) {
				return pointer;
			}
			if ((currentUsername.compareTo(username)) <0) {
				L = pointer;
				continue;
			}
			if ((currentUsername.compareTo(username)) >0 ) {
				R = pointer;
				continue;
			}
			return R;
		}
	}
	
	public Account get(int resultOfUsernameSearch) {
		return accounts.get[resultOfUsernameSearch];
	}
	
	public void add(Account a) {
		int i = 0;
		while ((a.getUsername.compareTo(accounts.get[i].getUsername()))<0) {
			i++;
		}
	
	}
}
