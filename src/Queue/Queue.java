package Queue;

public abstract class Queue<T> implements IQueue<T> {
    protected int top = -1;
    protected int base = -1;
    protected T[] data;

    public Queue(int size) {
        this.data = (T[]) new Object[size];
    }

    @Override
    public void clear() {
        this.top = -1;
        this.base = -1;
    }

    @Override
    public boolean isEmpty() {
        return this.top == this.base;
    }

    @Override
    public boolean isFull() {
        return this.top == this.data.length - 1;
    }
}
