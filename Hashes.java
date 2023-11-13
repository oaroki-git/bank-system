package bank.security;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hashes {
  private static byte[] get256ByteList (String plaintext) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      digest.update(plaintext.getBytes());
      return digest.digest();
    } catch (NoSuchAlgorithmException e) {
      return plaintext.getBytes();
    }
  }
  private static String byteArray2String (byte[] arr) {
    StringBuilder strbldr = new StringBuilder();
    for (byte b: arr) {
      strbldr.append(String.format("%02x", b));
    }
    return strbldr.toString();
  }
  public static String sha256 (String in) {
    return byteArray2String(get256ByteList(in));
  }
}
