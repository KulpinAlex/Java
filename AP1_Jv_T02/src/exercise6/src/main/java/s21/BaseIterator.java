package s21;

public interface BaseIterator<T> {

  T next();

  boolean hasNext();

  void reset();
}
