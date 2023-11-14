package bank.user;
import bank.user.Transanction;
import java.util.ArrayList;

public class Card {
  private static int total = 0;
  private static final int encodeKey = 0x7afcabed;

  private int id;
  private double balance;
  private ArrayList<Transanction> transanctions;

  private static int encodeID () {
    int[] integers = new int[4];
    int i=0;
    int integer = total^encodeKey;
    while (integer!=0) {
      integers[i]=integer%256;
      integer = (int) integer/256;
      i++;
    }
    return integers[3]*0xff + integers[0]*0xff00 + integers[1]*0xff0000 + integers[2]*0xff000000;
  } //it is SUPPOSED to return a negative numbers for small values, no surprises here.

  public Card () {
    this.id = encodeID();
    this.balance = 0;
    this.transanctions = new ArrayList<Transanction>();
    total++;
  }
  
  public int getID () {return id;}
  public double getBalance () {return balance;}
  public int deposit (double amount) {
    if (amount <= 0) {return -1;} //error: user is wasting CPU time.
    balance += amount;
    transanctions.add(new Transanction(amount, id));
    return 0;
  }
  public int withdraw (double amount) {
    if (amount > balance) {return -1;} //error: user going into debt.
    balance -= amount;
    transanctions.add(new Transanction(-amount, id));
    return 0;
  }

  public ArrayList<Transanction> getTransanctions () {return transanctions;}
}
