public class Manager extends Admin {
  public Manager (String username, String password, Admin admin) throws Exception {
    if (!admin.getStatus()) {throw new Exception("Admin not logged in")};
    this.username = username;
    this.salt = (int)(Math.random()*0xffff);
    this.passwordHash = hashString(password);
  }
}
