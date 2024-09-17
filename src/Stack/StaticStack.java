package Stack;

public class StaticStack<T> extends ArrayStack<T> {
    public StaticStack(int capacity) {
        super(capacity);
    }

    @Override
    public void push(T data) {
        if (isFull()) {
            throw new RuntimeException("Stack overflow");
        }
        this.data[++top] = data;
    }

    public boolean isFull() {
        return top == data.length - 1;
    }
}
