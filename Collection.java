package bank.user;
import bank.user.*;
import bank.security.ExtendedIterable;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class Collection implements ExtendedIterable<Account> {	
  public class AccountIterator implements Iterator<Account> {
    private int idx = 0;
    private final List<Account> list;
    public AccountIterator (List<Account> list) {this.list = list;}

    public boolean hasNext () {
      while (idx<list.size()) {
        if (!(list.get(idx) instanceof User)) {idx++;continue;}
	return true;
      } return false;
    }
    public Account next () throws NoSuchElementException {
      if (!hasNext()) {throw new NoSuchElementException("Stop Iteration");}
      return list.get(idx++);
    }
  }
  private ArrayList<Account> accounts = new ArrayList<>();	

  private int search(String username) {
    if (accounts.size()==0) {return 0;}
    if (username.compareTo(accounts.get(0).getUsername()) <=0){return 0;}

    int L = 0;
    int R = accounts.size();
    int pointer = -1;
    String currentUsername = " ";
    do {
      pointer = L+(R-L)/2;
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

  public Iterator<Account> iterator() {return new AccountIterator(accounts);}

  public void forEach(Consumer<? super Account> action) {return;} //bloat

  public static void main (String[] args) throws Exception {
    Collection accounts = new Collection();
    accounts.add(new Admin("root", "root"));
    Account acc = accounts.get("root");
    acc.login("root");
    accounts.add(new User("user0", "password", "addr", (Admin)acc));
    accounts.add(new User("user1", "password", "addr", (Admin)acc));
    for (Account user: accounts) {System.out.println(user);}
  }
}
