import java.util.Comparator;
import java.util.function.Predicate;

public class SLL<T> {
    private SLLnode<T> head;
    private int size;

    public SLLnode<T> getHead() {
        return head;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void append(T data) {
        SLLnode<T> node = new SLLnode<>(data);
        if (head == null) {
            head = node;
            size = 1;
            return;
        }
        SLLnode<T> current = head;
        while (current.next != null) {
            current = current.next;
        }
        current.next = node;
        size++;
    }

    public void insertSorted(T data, Comparator<T> comparator) {
        SLLnode<T> node = new SLLnode<>(data);
        if (head == null || comparator.compare(data, head.data) <= 0) {
            node.next = head;
            head = node;
            size++;
            return;
        }
        SLLnode<T> current = head;
        while (current.next != null && comparator.compare(data, current.next.data) > 0) {
            current = current.next;
        }
        node.next = current.next;
        current.next = node;
        size++;
    }

    public T getAt(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        int currentIndex = 0;
        SLLnode<T> current = head;
        while (current != null) {
            if (currentIndex == index) {
                return current.data;
            }
            currentIndex++;
            current = current.next;
        }
        return null;
    }

    public T removeAt(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        SLLnode<T> current = head;
        SLLnode<T> previous = null;
        int currentIndex = 0;
        while (current != null) {
            if (currentIndex == index) {
                if (previous == null) {
                    head = current.next;
                } else {
                    previous.next = current.next;
                }
                size--;
                return current.data;
            }
            previous = current;
            current = current.next;
            currentIndex++;
        }
        return null;
    }

    public T removeFirst(Predicate<T> predicate) {
        SLLnode<T> current = head;
        SLLnode<T> previous = null;
        while (current != null) {
            if (predicate.test(current.data)) {
                if (previous == null) {
                    head = current.next;
                } else {
                    previous.next = current.next;
                }
                size--;
                return current.data;
            }
            previous = current;
            current = current.next;
        }
        return null;
    }
}
