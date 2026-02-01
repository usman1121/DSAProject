# Browser Engine

This project implements a simple browser engine from scratch in Java, without using any Java library for stack, linked list, queue, or array list. Custom data structures are implemented to manage tabs, history, and memory.

## Features



- **Multiple Tabs**: Open, switch, and close tabs.
- **History Navigation**: Visit URLs, go back and forward in history.
- **Tab Recovery**: Restore recently closed tabs (LIFO order).
- **Memory Management**: Evicts oldest history entries from least recently used inactive tabs when memory limit is exceeded.

## Data Structures Used

- **Doubly Linked List**: For managing the list of open tabs.
- **Stack**: For storing closed tabs for restoration.
- **Doubly Linked List**: For tracking tab usage order (LRU for eviction).
- **Dynamic Array List**: For storing history in each tab.

## Custom Implementations

- `MyDoublyLinkedList<T>`: Doubly linked list with add, remove, get, etc.
- `MyStack<T>`: Stack with push, pop, peek.
- `MyDeque<T>`: Deque with addFirst, addLast, removeFirst, removeLast.
- `MyArrayList<T>`: Dynamic array list with add, remove, get, etc.

## How to Run

1. Compile the Java files:
   ```
   javac *.java
   ```

2. Run the main class:
   ```
   java Main
   ```

3. Follow the menu prompts to interact with the browser engine.

## Menu Options

- `n` - Open new tab
- `s` - Switch tab
- `v` - Visit URL
- `b` - Go back
- `f` - Go forward
- `c` - Close tab
- `r` - Restore tab
- `t` - Show status
- `x` - Exit

## Sample Usage

```
> n
Enter tab title: Research
Tab 'Research' created. Active.

> v
Enter URL: google.com
Current: google.com. History: [google.com].

> v
Enter URL: wikipedia.org
Current: wikipedia.org. History: [google.com, wikipedia.org].

> c
Closed 'Research'. Stored in Closed History.

> r
Restored 'Research'. Active.
History Preserved: [google.com, wikipedia.org].
```

## Memory Limit

The total history size across all open tabs is limited to 50 entries. When exceeded, the oldest entry from the least recently used inactive tab is evicted.