package tools;

public class Pair<T1, T2> {
  private final T1 first;
  private final T2 second;

  public Pair(T1 first, T2 second) {
    this.first = first;
    this.second = second;
  }

  public T1 first() {
    return first;
  }

  public T2 second() {
    return second;
  }

  public static <T1, T2> Pair<T1, T2> create(T1 first, T2 second) {
    return new Pair<>(first, second);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null)
      return false;

    if (obj == this)
      return true;

    if (obj instanceof Pair) {
      var other = (Pair)obj;

      //todo: remove this shit
      if (first.getClass() != other.first.getClass() || second.getClass() != other.second.getClass())
        return false;

      return first.equals(other.first) && second.equals(other.second);
    }

    return false;
  }

  @Override
  public int hashCode() {
    return (first.hashCode() * 1033) ^ second.hashCode();
  }
}
