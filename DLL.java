public class DLL<T> {
    private DLLnode<T> head;
    private DLLnode<T> tail;
    private int size;

    public DLLnode<T> getHead() {
        return head;
    }

    public int size() {
        return size;
    }

    public void append(T data) {
        DLLnode<T> node = new DLLnode<>(data);
        if (head == null) {
            head = node;
            tail = node;
            size = 1;
            return;
        }
        tail.next = node;
        node.prev = tail;
        tail = node;
        size++;
    }

    public T getAt(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        int currentIndex = 0;
        DLLnode<T> current = head;
        while (current != null) {
            if (currentIndex == index) {
                return current.data;
            }
            currentIndex++;
            current = current.next;
        }
        return null;
    }
}
