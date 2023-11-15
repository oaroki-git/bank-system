package bank.user;
import bank.user.Account;
import bank.security.Hashes;

public class Admin extends Account {
  private static int total = 0;
  
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
  
  //Admin class is not static, but there can only be one instance of it.
  //That is so that the instance can be stored and accessed alongside User instances
  //and that no one can create an Admin class with another set of credentials.
  public Admin (String username, String password) throws Exception {
    if (Admin.total!=0) {throw new Exception("Admin is already initialized");}
    this.username = username;
    this.salt = ""+((int)(Math.random()*0xffff));
    this.passwordHash = hashString(password);
    Admin.total = 1;
  }

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
