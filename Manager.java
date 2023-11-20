public class Manager extends Admin {
  public Manager (String username, String password, Admin admin) {
    if () {return };
    this.username = username;
    this.salt = (int)(Math.random()*0xffff);
    this.passwordHash = hashString(password);
  }
}
