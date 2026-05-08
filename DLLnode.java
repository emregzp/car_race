public class DLLnode<T> {
    public T data;
    public DLLnode<T> next;
    public DLLnode<T> prev;

    public DLLnode(T data) {
        this.data = data;
    }
}
