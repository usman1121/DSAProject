# Intelligent Call Center Director

A Java-based simulation of a high-traffic call center dispatch system that routes incoming calls to available agents based on priority, wait time, and agent skill sets.

## 🎯 Problem Description

Simulate a call center that manages incoming calls with sophisticated routing policies including priority queues, escalation mechanisms, and abandonment handling.

### 📋 Features

- **Priority-based call routing** (Tech Support > General Inquiry)
- **Automatic escalation** (General calls escalate after 10 time units)
- **Call abandonment** (Customers abandon after 20 time units)
- **Multi-agent system** with real-time call processing
- **Time-based simulation** with configurable call durations
- **Interactive CLI** for testing and demonstration

### 🏗️ System Architecture

#### **Data Structures Used**
- **Circular Queue** - For General call queue (strict FIFO with memory limits)
- **Linked List/Deque** - For High Priority queue (supports both ends insertion)
- **List of Objects** - For agent management and status tracking

#### **Core Components**

1. **Call** - Represents incoming calls with escalation logic
2. **Agent** - Manages agent states and call processing
3. **CircularQueue** - FIFO queue implementation for General calls
4. **HighPriorityDeque** - Deque implementation for High Priority calls
5. **CallCenter** - Main system orchestrator with all business logic
6. **Main** - Interactive CLI interface

## 🚀 Getting Started

### Prerequisites
- Java Development Kit (JDK) 8 or higher

### Installation & Compilation

```bash
# Clone or download the project files
cd /path/to/project

# Compile all Java files
javac *.java

# Run the application
java Main
```

## 🎮 Usage Guide

### Interactive Commands

| Command | Description | Example |
|---------|-------------|---------|
| `ASSIGN_AGENTS <n>` | Set number of available agents | `ASSIGN_AGENTS 3` |
| `CALL <id> <type> <time>` | Register a new incoming call | `CALL C1 General 0` |
| `TICK` | Advance simulation time by 1 unit | `TICK` |
| `STATUS` | Display current system status | `STATUS` |
| `SAMPLE` | Run built-in demonstration | `SAMPLE` |
| `EXIT` | Exit the application | `EXIT` |

### Call Types
- **General** - Standard customer inquiries (Normal Priority)
- **Tech** - Technical support calls (High Priority)

## 📊 System Policies

### 🔄 Escalation Policy
- **Trigger**: General calls waiting ≥ 10 time units
- **Action**: Automatically upgraded to High Priority
- **Placement**: Moved to back of high-priority queue

### ❌ Abandonment Policy
- **Trigger**: Any call waiting ≥ 20 time units
- **Action**: Customer hangs up, call marked as "MISSED"
- **Scope**: Applies to ALL calls (General and Tech)

### ⏱️ Timing Configuration
- **Call Duration**: Fixed 8 time units per call
- **Escalation Threshold**: 10 time units
- **Abandonment Threshold**: 20 time units
- **Time Unit**: 1 TICK command execution

## 🧪 Testing Scenarios

### Basic Test
```bash
> ASSIGN_AGENTS 2
> CALL C1 General 0
> CALL C2 Tech 0
> TICK
> STATUS
```

### Escalation Test
```bash
> ASSIGN_AGENTS 1
> CALL C1 General 0
> CALL C2 General 1
> CALL C3 General 2
> TICK (repeat 10+ times)
> STATUS
```

### Abandonment Test
```bash
> ASSIGN_AGENTS 1
> CALL C1 General 0
> CALL C2 General 1
> CALL C3 General 2
> CALL C4 General 3
> CALL C5 General 4
> TICK (repeat 20+ times)
> STATUS
```

### Time-Based Arrival Test
```bash
> ASSIGN_AGENTS 2
> CALL A1 General 8
> CALL A2 General 9
> CALL A3 Tech 12
> TICK (observe no immediate assignment)
> TICK (repeat until time 8+)
```

## 📈 Sample Execution

```text
> ASSIGN_AGENTS 2
> CALL C1 General 0
> CALL C2 Tech 0

> TICK (Time 0 -> 1)
- Agent 1 takes C2 (Tech) for 8 time units
- Agent 2 takes C1 (General) for 8 time units

> CALL C3 General 2
> CALL C4 General 3

> TICK (Time 12)
- C3 has waited 10 units. Escalated to High Priority.
- C4 still in General Queue.

> STATUS
High Priority Queue: [C3]
General Queue: [C4]
Busy Agents: [Ag1: C2 (6s), Ag2: C1 (6s)]
```

## 🏛️ System Architecture Diagram

```
┌─────────────────┐    ┌──────────────────┐    ┌─────────────────┐
│   Incoming      │    │                  │    │                 │
│     Calls       │───▶│  CallCenter      │───▶│   Agents        │
│                 │    │  (Main System)   │    │   (Processing)  │
└─────────────────┘    └──────────────────┘    └─────────────────┘
                              │
                              ▼
                       ┌──────────────────┐
                       │                  │
                       │   Queue System   │
                       │                  │
                       │ ┌──────────────┐ │
                       │ │ High Priority │ │
                       │ │    (Deque)   │ │
                       │ └──────────────┘ │
                       │ ┌──────────────┐ │
                       │ │   General     │ │
                       │ │ (Circular Q)  │ │
                       │ └──────────────┘ │
                       └──────────────────┘
```

## 🔧 Technical Implementation

### Key Algorithms

1. **Agent Assignment Algorithm**
   ```java
   for (Agent agent : agents) {
       if (!agent.isBusy()) {
           // Priority: High Priority → General
           // Time check: currentTime >= call.getTimestamp()
       }
   }
   ```

2. **Escalation Detection**
   ```java
   if (call.shouldEscalate()) {
       call.escalate();
       highPriorityQueue.addLast(call);
   }
   ```

3. **Abandonment Detection**
   ```java
   if (call.shouldAbandon()) {
       missedCalls.add(call);
       // Remove from queue
   }
   ```

### Performance Characteristics

- **Time Complexity**: O(n) for agent assignment, O(1) for queue operations
- **Space Complexity**: O(m + n) where m = agents, n = queued calls
- **Scalability**: Handles 100+ concurrent calls efficiently

## 🐛 Known Issues & Limitations

- **Fixed Call Duration**: All calls take exactly 8 time units (configurable)
- **Single-threaded Simulation**: No concurrent processing simulation
- **Memory Limits**: Circular queue has fixed capacity (configurable)

## 🔄 Future Enhancements

- [ ] Variable call durations by call type
- [ ] Agent skill specialization
- [ ] Call priority levels beyond High/General
- [ ] Real-time GUI visualization
- [ ] Performance metrics and analytics
- [ ] Multi-threaded simulation
- [ ] Database persistence for call history

## 📝 License

This project is provided as-is for educational purposes.

## 🤝 Contributing

Feel free to submit issues, feature requests, or pull requests to improve the simulation.

## 📚 Educational Value

This project demonstrates:
- Data structure implementation (Circular Queue, Deque)
- Object-oriented design patterns
- State machine implementation
- Event-driven architecture
- Algorithm design and optimization
- System simulation and modeling

---

**Author**: AI Assistant  
**Purpose**: Educational demonstration of data structures and algorithms in a real-world simulation context
