package bank.user;
import bank.user.Account;
import bank.security.Hashes;

public class Admin extends Account {
  private static boolean rootCreated = false;
  
  private final String username;
  private final String salt;
  private String passwordHash;
  private boolean loggedIn = false;

  private String hashString (String in) {
    return Hashes.sha256(in+salt);
  }
  private boolean verify (String password) {
    if (!passwordHash.equals(hashString(password))) {return false;}
    return true;
  }
  
  public Admin (String username, String password) throws Exception {
    if (rootCreated) {throw new Exception("Root admin is already initialized.");}
    this.username = username;
    this.salt = ""+((int)(Math.random()*0xffff));
    this.passwordHash = hashString(password);
    rootCreated = true;
  } //constructor for root admin.
  public Admin (String username, String password, Admin admin) throws Exception {
    if (!admin.getStatus()) {throw new Exception("Provided admin is not logged in.");}
    this.username = username;
    this.salt = ""+((int)(Math.random()*0xffff));
    this.passwordHash = hashString(password);
  } //constructor for other admins.

  //Admin doesn't have any fields that require protection from unlogged accessing;
  //however, its loggedIn variable must be accessible to secure admin access to User.
  public String getUsername () {return username;}
  public int setPassword (String oldpassword, String newpassword) {
    if (!verify(oldpassword)) {return 1;}
    passwordHash = hashString(newpassword); return 0;
  }
  public int login (String password) {
    if (!verify(password)) {return 1;}
    loggedIn = true;
    return 0;
  }
  public int logout () {
    loggedIn = false; return 0;
  }
  public boolean getStatus () {return loggedIn;}
}
