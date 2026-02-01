# Problem 03: The Intelligent Call Center Director

## Problem Description
Simulate a high-traffic call center dispatch system. The system must route incoming calls to available agents based on priority, wait time, and agent skill sets.

### The System Rules
1.  **Incoming Traffic**: Calls arrive with a `timestamp`, `customer_id`, and `type` (General or Tech).
2.  **Priority Queues**:
    *   **High Priority**: "Tech Support" calls.
    *   **Normal Priority**: "General Inquiry" calls.
3.  **Escalation Policy**:
    *   If a "General" call waits for more than **10 time units**, it is automatically upgraded to "High Priority" and moved to the *back* of the high-priority queue.
4.  **Abandonment Policy**:
    *   If any customer waits more than **20 time units**, they hang up (removed from queue) and are logged as "MISSED".
5.  **Agent Logic**:
    *   There are `N` agents.
    *   Agents always take High Priority calls first.
    *   Calls take a variable amount of time to process.

## Must Use Data Structures
*   **Circular Queue**: For the "General" call queue (to enforce strict FIFO and memory limits).
*   **Linked List / Deque**: For the "High Priority" queue (allowing insertion from both General upgrades and direct Tech calls).
*   **List of Objects**: To represent specific Agents and their busy/free status chains.

## Operations to Implement (CLI Commands)
*   `CALL <id> <type> <time>`: Register a new incoming call.
*   `ASSIGN_AGENTS <n>`: Set number of available agents.
*   `TICK`: Advance time by 1 unit.
    *   Process Agent-Customer interactions (decrement call duration).
    *   Check for Escalations (General -> High).
    *   Check for Abandonment (Remove expired calls).
*   `STATUS`: Show queues, active calls, and missed call count.

## Sample Execution

```text
> ASSIGN_AGENTS 2
> CALL C1 General 0
> CALL C2 Tech 0

> TICK (Time 0 -> 1)
- Agent 1 takes C2 (Tech).
- Agent 2 takes C1 (General).

> CALL C3 General 2
> CALL C4 General 3

... (Time passes, Agents busy) ...

> TICK (Time 12)
- C3 has waited 10 units. Escalated to High Priority.
- C4 still in General Queue.

> STATUS
High Priority Queue: [C3]
General Queue: [C4]
Busy Agents: [Ag1: C2, Ag2: C1]
```
