package bank.user;

public abstract class Account {
  public abstract String getUsername ();
  public abstract int login (String password);
  public abstract int logout ();
  public abstract boolean getStatus ();
}
