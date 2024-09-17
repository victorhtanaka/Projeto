package Stack;

public interface IStack<T> {
    void push(T data);
    T pop();
    void clear();
    boolean isEmpty();
}
