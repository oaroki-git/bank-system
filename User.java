package bank.user;
import bank.user.Account;
import bank.user.Admin;
import bank.security.Hashes;
import java.util.ArrayList;

public class User extends Account {
  private final String username;
  private final String salt;
  private String passwordHash;
  private boolean loggedIn = false;
  private boolean asUser = false; 
  private String address;
  private ArrayList<Card> cards = new ArrayList<Card>();
  private ArrayList<Integer> cardIDs = new ArrayList<Integer>();

  private static ArrayList<Card> allCards = new ArrayList<Card>();
  private static ArrayList<String[]> allCardIDs = new ArrayList<String[]>();

  //client-inaccessible methods
    //password-related
  private String hashString (String in) {
    return Hashes.sha256(in+salt);
  } //hashes the input String with constant random salt.
  private boolean verify (String password) {
    if (!passwordHash.equals(hashString(password))) {return false;}
    return true;
  } //shorthand for password verification.
    //Card management related
  private int search (int id) {
    if (cards.size()==0) {return 0;}
    if (cards.get(0).getID()>=id) {return 0;}
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
  private static int globalSearch (int id) {
    if (allCards.size()==0) {return 0;}
    if (allCards.get(0).getID()>=id) {return 0;}
    int L = 0;
    int R = allCards.size();
    int pointer = -1;
    int currentID = -1;
    do {
      pointer = L+(R-L)/2;
      currentID = allCards.get(pointer).getID();
      if (currentID == id) {return pointer;}
      if (currentID < id) {L = pointer;continue;}
      if (currentID > id) {R = pointer;continue;}
    } while ((R-L)!=1); return R;
  }
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
  public int setPassword (String newpassword) throws Exception {
    if (!loggedIn) {throw new Exception("Not logged in.");}
    if (!asUser) {return 1;}
    passwordHash = hashString(newpassword);
    return 0;
  } //Setting the password. Admin cannot change the password of any User.
  public int setAddress (String address) throws Exception {
    if (!loggedIn) {throw new Exception("Not logged in.");}
    if (!asUser) {return 1;}
    this.address = address;
    return 0;
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
    if (!asUser) {return 1;}
    Card card = new Card(password);
    int idx = search(card.getID());
    cards.add(idx, card);
    allCards.add(idx, card);
    idx = globalSearch(card.getID());
    cardIDs.add(card.getID());
    String[] pair = {this.getUsername(), ""+card.getID()};
    allCardIDs.add(idx, pair);
    return 0;
  } //Admin cannot add card to any User.
//public ArrayList<Card> getCards () throws Exception {
//  if (!loggedIn) {throw new Exception("Not logged in.");}
//  return cards;
//}
  public ArrayList<Integer> getIDs () throws Exception {
    if (!loggedIn) {throw new Exception("Not logged in.");}
    return cardIDs;
  }
  public static ArrayList<String[]> getAllIDs (Admin admin) throws Exception {
    if (!admin.getStatus()) {throw new Exception("Provided admin not logged in.");}
    return allCardIDs;
  }
  public static Card getCardGlobal (int id, Admin admin) throws Exception {
    if (!admin.getStatus()) {throw new Exception("Provided admin not logged in.");}
    int idx = globalSearch(id);
    if (idx==(allCards.size())) {return null;}
    Card card = allCards.get(idx);
    if (card.getID()==id) {return card;}
    return null; //no such instance
  } 

  //Public methods usable without loggedIn = true.
  public int login (String password) {
    if (!verify(password)) {return 1;}
    loggedIn = true; asUser = true;return 0;
  } //Normal login.
  public int adminAccess (Admin admin) {
    if (!admin.getStatus()) {return 1;}
    loggedIn = true;
    return 0;
  } //Admin gets access to Card instances of Users.
  public int logout () {
    loggedIn = false; asUser = false; return 0;
  }
  public String getUsername () {return username;}
  public String getAddress () {return address;}
  public boolean getStatus () {return loggedIn;}
}
