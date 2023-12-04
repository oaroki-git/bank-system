package bank;
import java.util.Scanner;

public class IO {
public static void print (String content) {
  System.out.print(content);
}
public static String input (String prompt) {
  Scanner s = new Scanner(System.in);
  print(prompt);
  return s.nextLine();
}
public static void main (String[] args) {
  print("Testing something\n");
  String str = input("Tell me what are you doing!\n");
  print("You are "+str+"\n");
}
}
