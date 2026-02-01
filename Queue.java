public class Queue<E> {
    private Node<E> front;
    private Node<E> rear;
    private int size;

    public Queue() {
        front = null;
        rear = null;
        size = 0;
    }

    public void addFirst(E data) {
        Node<E> newNode = new Node<>(data);
        if (front == null) {
            front = newNode;
            rear = newNode;
        } else {
            newNode.next = front;
            front.prev = newNode;
            front = newNode;
        }
        size++;
    }

    public void addLast(E data) {
        Node<E> newNode = new Node<>(data);
        if (rear == null) {
            front = newNode;
            rear = newNode;
        } else {
            rear.next = newNode;
            newNode.prev = rear;
            rear = newNode;
        }
        size++;
    }

    public E removeFirst() {
        if (front == null) return null;
        E data = front.data;
        if (front == rear) {
            front = null;
            rear = null;
        } else {
            front = front.next;
            front.prev = null;
        }
        size--;
        return data;
    }

    public E removeLast() {
        if (rear == null) return null;
        E data = rear.data;
        if (front == rear) {
            front = null;
            rear = null;
        } else {
            rear = rear.prev;
            rear.next = null;
        }
        size--;
        return data;
    }

    public E peekFirst() {
        return front != null ? front.data : null;
    }

    public E peekLast() {
        return rear != null ? rear.data : null;
    }

    public boolean isEmpty() {
        return front == null;
    }

    public int size() {
        return size;
    }

    // For tab usage, need to remove a specific item
    public boolean remove(E data) {
        Node<E> current = front;
        while (current != null) {
            if (current.data.equals(data)) {
                if (current.prev != null) {
                    current.prev.next = current.next;
                } else {
                    front = current.next;
                }
                if (current.next != null) {
                    current.next.prev = current.prev;
                } else {
                    rear = current.prev;
                }
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }
}