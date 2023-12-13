package bank.security;

public interface ExtendedIterable<T> extends Iterable<T> {
  public T get (String str);
  public void add (T element) throws Exception;
}
