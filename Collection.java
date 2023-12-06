package bank.user;
//import bank.user.Account;
import bank.user.*;
import bank.IO;
import java.util.ArrayList;

public class Collection {	
  ArrayList<Account> accounts = new ArrayList<>();	

  private int search(String username) {
    if (accounts.size()==0) {return 0;}
    if (username.compareTo(accounts.get(0).getUsername()) <0){return 0;}
    //if (accounts.get(accounts.size()-1).getUsername<=username) {return accounts.size();} //handy but excessive
    int L = 0;
    int R = accounts.size();
    int pointer = -1;
    String currentUsername = " ";
    do {
      pointer = L+(L-R)/2;
      currentUsername = accounts.get(pointer).getUsername();
      if (currentUsername.equals(username)) {return pointer;}
      if ((currentUsername.compareTo(username)) >0) {R = pointer;continue;}
      if ((currentUsername.compareTo(username)) <0) {L = pointer;continue;}
    } while ((R-L!=1)); return R;
  }

  public Account get(String username) {
    int idx = search(username);
    if (idx==accounts.size()) {return null;}
    Account acc = accounts.get(idx);
    if (!username.equals(acc.getUsername())) {return null;}
    return acc;
  }

  public void add(Account acc) throws Exception {
    if (!(get(acc.getUsername())==null)) {throw new Exception("Username Collision.");}
    accounts.add(search(acc.getUsername()), acc);
  }

  public static void main (String[] args) throws Exception {
    Collection accounts = new Collection();
    accounts.add(new Admin("root", "root"));
    Account acc = accounts.get("root");
    IO.print(acc.toString());
  } 
}
