class Node<E> {
    E data;
    Node<E> prev;
    Node<E> next;

    public Node(E data) {
        this.data = data;
        this.prev = null;
        this.next = null;
    }
}

public class MyDoublyLinkedList<E> {
    private Node<E> head;
    private Node<E> tail;
    private int size;

    public MyDoublyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    public void add(E data) {
        Node<E> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    public void addFirst(E data) {
        Node<E> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    public E remove(E data) {
        Node<E> current = head;
        while (current != null) {
            if (current.data.equals(data)) {
                if (current.prev != null) {
                    current.prev.next = current.next;
                } else {
                    head = current.next;
                }
                if (current.next != null) {
                    current.next.prev = current.prev;
                } else {
                    tail = current.prev;
                }
                size--;
                return current.data;
            }
            current = current.next;
        }
        return null;
    }

    public E get(int index) {
        if (index < 0 || index >= size) return null;
        Node<E> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    // For iteration, but since no Iterator, perhaps add a method to get all as array or something
    // For simplicity, add a method to find by title, assuming E is Tab
    public E find(String title) {
        Node<E> current = head;
        while (current != null) {
            if (((Tab) current.data).title.equals(title)) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }

    public E getLast() {
        return tail != null ? tail.data : null;
    }

    public E removeLast() {
        if (tail == null) return null;
        E data = tail.data;
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }
        size--;
        return data;
    }
}