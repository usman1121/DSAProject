public class MyArrayList<E> {
    private Object[] array;
    private int size;
    private static final int INITIAL_CAPACITY = 10;

    public MyArrayList() {
        array = new Object[INITIAL_CAPACITY];
        size = 0;
    }

    public void add(E data) {
        if (size == array.length) {
            resize();
        }
        array[size++] = data;
    }

    public void add(int index, E data) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        if (size == array.length) {
            resize();
        }
        for (int i = size; i > index; i--) {
            array[i] = array[i - 1];
        }
        array[index] = data;
        size++;
    }

    public E get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        return (E) array[index];
    }

    public E remove(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        E data = (E) array[index];
        for (int i = index; i < size - 1; i++) {
            array[i] = array[i + 1];
        }
        array[--size] = null;
        return data;
    }

    public boolean remove(E data) {
        for (int i = 0; i < size; i++) {
            if (array[i].equals(data)) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean contains(E data) {
        for (int i = 0; i < size; i++) {
            if (array[i].equals(data)) {
                return true;
            }
        }
        return false;
    }

    public int indexOf(E data) {
        for (int i = 0; i < size; i++) {
            if (array[i].equals(data)) {
                return i;
            }
        }
        return -1;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void resize() {
        Object[] newArray = new Object[array.length * 2];
        System.arraycopy(array, 0, newArray, 0, size);
        array = newArray;
    }

    // For history, need to clear from index to end
    public void removeFrom(int index) {
        for (int i = index; i < size; i++) {
            array[i] = null;
        }
        size = index;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(array[i]);
            if (i < size - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}