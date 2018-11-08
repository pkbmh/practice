package tree;

public interface Tree<T> {
    boolean insert(T value);
    T search();
    boolean delete(T value);
}
