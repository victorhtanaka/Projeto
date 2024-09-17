package Queue;

public class StaticQueue<T> extends Queue<T> {
    public StaticQueue(int size) {
        super(size);
    }
    @Override
    public void store(T data) {
        try {
            this.data[++top] = data;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T retrieve() {
        try {
            if (this.top == this.base) {
                return this.data[base];
            }
            return this.data[++base];
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
