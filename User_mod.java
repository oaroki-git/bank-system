package bank.user;
import bank.user.Account;
import bank.security.Hashes;
import java.util.ArrayList;

public class User extends Account {
  private final String username;
  private final String salt;
  private String passwordHash;
  private boolean loggedIn = false;
  private String address;
  private ArrayList<Card> cards = new ArrayList<Card>();

  //client-inaccessible methods
    //password-related
  private String hashString (String in) {
    return Hashes.sha256(in+salt);
  } //hashes the input String with constant random salt.
//  private boolean verify (String password) {
//    if (!passwordHash.equals(hashString(password))) {return false;}
//    return true;
//  } //shorthand for password verification.
    private void verify (String password) throws SecurityException {
      if (!passwordHash.equals(hashString(password))) {throw new SecurityException("Wrong Password");}}
    //Card management related
  private int search (int id) {
    if (cards.size()==0) {return 0;}
    if (cards.get(0).getID()>=id) {return 0;}
    //if (cards.get(cards.size()-1).getID()<=id) {return cards.size();} //not necessary, but handy nonetheless.
    int L = 0;
    int R = cards.size();
    int pointer = -1;
    int currentID = -1;
    do {
      pointer = L+(R-L)/2;
      currentID = cards.get(pointer).getID();
      if (currentID == id) {return pointer;}
      if (currentID < id) {L = pointer;continue;}
      if (currentID > id) {R = pointer;continue;}
    } while ((R-L)!=1); return R;
  } //binary search for Card with closest id to the input, returns index.
  /*
  private void insert (int idx, Card card) {
    cards.add(null);
    for (int i=cards.size()-2;i>=idx;i--) {
      cards.set(i+1, cards.get(i));
    }
    cards.set(idx, card);
  } //inserts created Card object to given index.
  *///it was unnecessary, slower than Arraylist<T>.add(int idx, T obj)

  public User (String username, String password, String address, Admin admin) throws Exception {
    if (!admin.getStatus()) {
      throw new Exception("Given admin not logged in.");
    }
    this.username = username;//collections guy needs to verify if username is taken
    this.salt = ""+((int)(Math.random()*0xffff));
    this.passwordHash = hashString(password);
    this.address = address;
  } //constructor, only loggedIn admin can execute

  //Changing credentials
  public void setPassword (String oldpassword, String newpassword) throws Exception {
    if (!loggedIn) {throw new Exception("Not logged in.");}
    verify(oldpassword); 
    passwordHash = hashString(newpassword);
  } //Setting the password. Admin cannot change the password of any User.
  public void setAddress (String password, String address) throws Exception {
    if (!loggedIn) {throw new Exception("Not logged in.");}
    verify(password);
    this.address = address;
  } //address related stuff. Admin cannot change address of any User.

  //Card related stuff
  public Card getCard (int id) throws Exception {
    if (!loggedIn) {throw new Exception("Not logged in.");}
    int idx = search(id);
    if (idx==(cards.size())) {return null;}
    Card card = cards.get(idx);
    if (card.getID()==id) {return card;}
    return null; //no such instance
  }
  public int addCard (String password) throws Exception {
    if (!loggedIn) {throw new Exception("Not logged in.");}
    verify(password);
    Card card = new Card(password);
    cards.add(search(card.getID()), card);
    return card.getID(); //will return a negative number 
  } //Admin cannot add card to any User.
  public ArrayList<Card> getCards () throws Exception {
    if (!loggedIn) {throw new Exception("Not logged in.");}
    return cards;
  }
  public ArrayList<Integer> getIDs () throws Exception {
    if (!loggedIn) {throw new Exception("Not logged in.");}
    ArrayList<Integer> ids = new ArrayList<Integer>();
    for (Card card: cards) {
      ids.add(card.getID());
    } return ids;
  }

  //Public methods usable without loggedIn = true.
  public int login (String password) {
    try {
    verify(password);} catch (SecurityException e) {
    return 1;
    }
    loggedIn = true;return 0;
  } //Normal login.
  public int adminAccess (Admin admin) {
    if (!admin.getStatus()) {return 1;}
    loggedIn = true;
    return 0;
  } //Admin gets access to Card instances of Users.
  public int logout () {
    loggedIn = false; return 0;
  }
  public String getUsername () {return username;}
  public String getAddress () {return address;}
  public boolean getStatus () {return loggedIn;}
}
