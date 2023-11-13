package bank.user;
import bank.security.Hashes;
import java.util.ArrayList;

public class User {
  private String passwordHash;
  private String username;
  private static String salt = "#rust-cargo>cmake";
  private String address;
  private boolean admin = false;
  private ArrayList<Card> cards = new ArrayList<Card>();
  
  public User (String username, String password, String address) {
    this.username = username;//collections guy needs to verify if username is taken
    this.passwordHash = Hashes.sha256(password+salt);
    this.address = address;
  }

  public Card getCard (int id) {return }

  public Card addCard (Card card) {return;}

  public boolean verify (String password) {
    if (Hashes.sha256(password+salt)==passwordHash) {
      return true;
    }
    return false;
  }

  public boolean auth () {
    return admin;
  }
}
