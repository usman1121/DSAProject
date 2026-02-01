# Problem 35: Fault-Tolerant Load Balancer

## Description
This project implements a simple fault-tolerant load balancer using a **Circular Linked List**.
It distributes incoming requests across multiple servers in a **round-robin** manner.
If a server is marked as dead, the system skips it and sends the request to the next active server.

## Features
- Add servers to the load balancer
- Mark servers as DEAD
- Revive DEAD servers
- Dispatch requests only to ACTIVE servers
- Uses a Circular Linked List as required

## Data Structure Used
- Circular Linked List
- Each node represents a server
- Each server has two states: ACTIVE or DEAD

## Commands
The program runs in the command line and supports the following commands:

- `ADD_SERVER <name>` → Adds a new server
- `KILL_SERVER <name>` → Marks a server as DEAD
- `REVIVE_SERVER <name>` → Revives a DEAD server
- `DISPATCH_REQUEST` → Sends a request to the next active server
- `EXIT` → Exits the program

## How to Run

1. Open Command Prompt

2. Navigate to the project folder

3. Compile the program:
javac LoadBalancer.java

4. Run the program:
java LoadBalancer  