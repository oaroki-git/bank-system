package bank;
import java.util.Scanner;


public class IO {
    // Reset
    public static final String RESET = "\033[0m";  // Text Reset

    // colors
    public static final String BLACK = "\033[0;30m";   // BLACK
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static final String BLUE = "\033[0;34m";    // BLUE
    public static final String PURPLE = "\033[0;35m";  // PURPLE
    public static final String CYAN = "\033[0;36m";    // CYAN
    public static final String WHITE = "\033[0;37m";   // WHITE

    // keyword: "bold"
    public static final String BLACK_BOLD = "\033[1;30m";  // BLACK
    public static final String RED_BOLD = "\033[1;31m";    // RED
    public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
    public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
    public static final String BLUE_BOLD = "\033[1;34m";   // BLUE
    public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE
    public static final String CYAN_BOLD = "\033[1;36m";   // CYAN
    public static final String WHITE_BOLD = "\033[1;37m";  // WHITE

    // keyword: "underline"
    public static final String BLACK_UNDERLINED = "\033[4;30m";  // BLACK
    public static final String RED_UNDERLINED = "\033[4;31m";    // RED
    public static final String GREEN_UNDERLINED = "\033[4;32m";  // GREEN
    public static final String YELLOW_UNDERLINED = "\033[4;33m"; // YELLOW
    public static final String BLUE_UNDERLINED = "\033[4;34m";   // BLUE
    public static final String PURPLE_UNDERLINED = "\033[4;35m"; // PURPLE
    public static final String CYAN_UNDERLINED = "\033[4;36m";   // CYAN
    public static final String WHITE_UNDERLINED = "\033[4;37m";  // WHITE

    // keyword: "highlight"
    public static final String BLACK_BACKGROUND = "\033[40m";  // BLACK
    public static final String RED_BACKGROUND = "\033[41m";    // RED
    public static final String GREEN_BACKGROUND = "\033[42m";  // GREEN
    public static final String YELLOW_BACKGROUND = "\033[43m"; // YELLOW
    public static final String BLUE_BACKGROUND = "\033[44m";   // BLUE
    public static final String PURPLE_BACKGROUND = "\033[45m"; // PURPLE
    public static final String CYAN_BACKGROUND = "\033[46m";   // CYAN
    public static final String WHITE_BACKGROUND = "\033[47m";  // WHITE

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
  print("highlight","red","hello");
  print("bold","blue","world");
  print("ahsdf");
  print("purple","purple");
}

// esther's edits below
public static void print (String formatType, String color, String str) {
  //this method overloads print, if a formatType and color is mentioned, content will be printed and formatted with a specific color
  if (formatType.equals("bold")) { //checks formatType
    if (color.equals("black")) {System.out.println(BLACK_BOLD+str+RESET);} //checks color
    if (color.equals("red")) {System.out.println(RED_BOLD+str+RESET);}
    if (color.equals("green")) {System.out.println(GREEN_BOLD+str+RESET);}
    if (color.equals("yellow")) {System.out.println(YELLOW_BOLD+str+RESET);}
    if (color.equals("blue")) {System.out.println(BLUE_BOLD+str+RESET);}
    if (color.equals("purple")) {System.out.println(PURPLE_BOLD+str+RESET);}
    if (color.equals("cyan")) {System.out.println(CYAN_BOLD+str+RESET);}
    if (color.equals("white")) {System.out.println(WHITE_BOLD+str+RESET);}
  }
  if (formatType.equals("underline")) {
    if (color.equals("black")) {System.out.println(BLACK_UNDERLINED+str+RESET);}
    if (color.equals("red")) {System.out.println(RED_UNDERLINED+str+RESET);}
    if (color.equals("green")) {System.out.println(GREEN_UNDERLINED+str+RESET);}
    if (color.equals("yellow")) {System.out.println(YELLOW_UNDERLINED+str+RESET);}
    if (color.equals("blue")) {System.out.println(BLUE_UNDERLINED+str+RESET);}
    if (color.equals("purple")) {System.out.println(PURPLE_UNDERLINED+str+RESET);}
    if (color.equals("cyan")) {System.out.println(CYAN_UNDERLINED+str+RESET);}
    if (color.equals("white")) {System.out.println(WHITE_UNDERLINED+str+RESET);}
  }
  if (formatType.equals("highlight")) {
    if (color.equals("black")) {System.out.println(BLACK_BACKGROUND+str+RESET);}
    if (color.equals("red")) {System.out.println(RED_BACKGROUND+str+RESET);}
    if (color.equals("green")) {System.out.println(GREEN_BACKGROUND+str+RESET);}
    if (color.equals("yellow")) {System.out.println(YELLOW_BACKGROUND+str+RESET);}
    if (color.equals("blue")) {System.out.println(BLUE_BACKGROUND+str+RESET);}
    if (color.equals("purple")) {System.out.println(PURPLE_BACKGROUND+str+RESET);}
    if (color.equals("cyan")) {System.out.println(CYAN_BACKGROUND+str+RESET);}
    if (color.equals("white")) {System.out.println(WHITE_BACKGROUND+str+RESET);}
  }
}
public static void print (String color, String str) {
    if (color.equals("black")) {System.out.println(BLACK+str+RESET);}
    if (color.equals("red")) {System.out.println(RED+str+RESET);}
    if (color.equals("green")) {System.out.println(GREEN+str+RESET);}
    if (color.equals("yellow")) {System.out.println(YELLOW+str+RESET);}
    if (color.equals("blue")) {System.out.println(BLUE+str+RESET);}
    if (color.equals("purple")) {System.out.println(PURPLE+str+RESET);}
    if (color.equals("cyan")) {System.out.println(CYAN+str+RESET);}
    if (color.equals("white")) {System.out.println(WHITE+str+RESET);}
    else {System.out.println(str);}
}
}
