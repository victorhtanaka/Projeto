package Stack;

public class DynamicStack<T> extends ArrayStack<T>  {
    public DynamicStack(int capacity) {
        super(capacity);
    }

    @Override
    public void push(T data) {
        try {
            if (this.top == this.data.length - 1) {
                Object[] newData = new Object[this.data.length * 2];
    
                for (int i = 0; i <= this.top; i++) {
                    newData[i] = this.data[i];
                }
                this.data = (T[]) newData;
            }
            this.data[++top] = data;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
