package Queue;

public interface IQueue<T> {
    void store(T data);
    T retrieve();
    void clear();
    boolean isEmpty();
    boolean isFull();
}
