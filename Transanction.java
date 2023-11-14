package bank.user;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transanction {
  private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
  private final int id;
  private final LocalDateTime time;
  private final double amount;//positive for deposit, negative for withdrawal.

  public Transanction (double amount, int id) {
    this.time = LocalDateTime.now();
    this.amount = amount;
    this.id = id;
  }
  public double getAmount () {return amount;}
  public LocalDateTime getTime () {return time;}
  public int getID () {return id;}
  public String toString () {
    return time.format(formatter)+": "+id+": "+amount;
  }

  public static void main (String[] args) {
    Transanction trans = new Transanction(100.0, 22935960);
    System.out.println(trans);
  }
}
