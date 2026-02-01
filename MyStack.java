public class MyStack<E> {
    private Node<E> top;
    private int size;

    public MyStack() {
        top = null;
        size = 0;
    }

    public void push(E data) {
        Node<E> newNode = new Node<>(data);
        newNode.next = top;
        top = newNode;
        size++;
    }

    public E pop() {
        if (top == null) return null;
        E data = top.data;
        top = top.next;
        size--;
        return data;
    }

    public E peek() {
        return top != null ? top.data : null;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return size;
    }
}