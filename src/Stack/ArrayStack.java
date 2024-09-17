package Stack;

public abstract class ArrayStack<T> implements IStack<T> {
    protected int top = -1;
    protected T[] data;

    public ArrayStack(int capacity) {
        this.data = (T[]) new Object[capacity];
    }

    @Override
    public T pop() {
        if (isEmpty()) {
            throw new RuntimeException("Stack underflow");
        }
        return this.data[top--];
    }

    @Override
    public void clear() {
        this.top = -1;
    }

    @Override
    public boolean isEmpty() {
        return top == -1;
    }
}
