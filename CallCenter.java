import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CallCenter {
    private CircularQueue<Call> generalQueue;
    private HighPriorityDeque<Call> highPriorityQueue;
    private List<Agent> agents;
    private List<Call> missedCalls;
    private int currentTime;
    public CallCenter(int generalQueueCapacity) {
        this.generalQueue = new CircularQueue<>(generalQueueCapacity);
        this.highPriorityQueue = new HighPriorityDeque<>();
        this.agents = new ArrayList<>();
        this.missedCalls = new ArrayList<>();
        this.currentTime = 0;
        new Random();
    }
    
    public void assignAgents(int n) {
        agents.clear();
        for (int i = 1; i <= n; i++) {
            agents.add(new Agent(i));
        }
        System.out.println("Assigned " + n + " agents");
    }
    
    public void receiveCall(String customerId, String type, int timestamp) {
        Call call = new Call(customerId, type, timestamp);
        
        if (type.equals("Tech")) {
            highPriorityQueue.addFirst(call);
        } else {
            generalQueue.enqueue(call);
        }
        
        System.out.println("Call " + customerId + " (" + type + ") received at time " + timestamp);
    }
    
    public void tick() {
        currentTime++;
        System.out.println("\n--- TICK: Time " + (currentTime - 1) + " -> " + currentTime + " ---");
        
        processAgentCalls();
        updateWaitTimes();
        checkEscalations();
        checkAbandonments();
        assignCallsToAgents();
        
        printTickSummary();
    }
    
    private void processAgentCalls() {
        for (Agent agent : agents) {
            if (agent.isBusy()) {
                agent.processCall();
                if (!agent.isBusy()) {
                    System.out.println("Agent " + agent.getId() + " completed call");
                }
            }
        }
    }
    
    private void updateWaitTimes() {
        List<Call> generalCalls = new ArrayList<>();
        while (!generalQueue.isEmpty()) {
            Call call = generalQueue.dequeue();
            // Only increment wait time if call has actually arrived
            if (currentTime >= call.getTimestamp()) {
                call.incrementWaitTime();
            }
            generalCalls.add(call);
        }
        for (Call call : generalCalls) {
            generalQueue.enqueue(call);
        }
        
        List<Call> highPriorityCalls = new ArrayList<>();
        while (!highPriorityQueue.isEmpty()) {
            Call call = highPriorityQueue.removeFirst();
            // Only increment wait time if call has actually arrived
            if (currentTime >= call.getTimestamp()) {
                call.incrementWaitTime();
            }
            highPriorityCalls.add(call);
        }
        for (Call call : highPriorityCalls) {
            highPriorityQueue.addLast(call);
        }
    }
    
    private void checkEscalations() {
        List<Call> generalCalls = new ArrayList<>();
        while (!generalQueue.isEmpty()) {
            Call call = generalQueue.dequeue();
            // Only check escalation for calls that have arrived
            if (currentTime >= call.getTimestamp() && call.shouldEscalate()) {
                call.escalate();
                highPriorityQueue.addLast(call);
                System.out.println("Call " + call.getCustomerId() + " escalated to High Priority after " + call.getWaitTime() + " time units");
            } else {
                generalCalls.add(call);
            }
        }
        for (Call call : generalCalls) {
            generalQueue.enqueue(call);
        }
    }
    
    private void checkAbandonments() {
        List<Call> generalCalls = new ArrayList<>();
        while (!generalQueue.isEmpty()) {
            Call call = generalQueue.dequeue();
            // Only check abandonment for calls that have arrived
            if (currentTime >= call.getTimestamp() && call.shouldAbandon()) {
                missedCalls.add(call);
                System.out.println("Call " + call.getCustomerId() + " ABANDONED after " + call.getWaitTime() + " time units");
            } else {
                generalCalls.add(call);
            }
        }
        for (Call call : generalCalls) {
            generalQueue.enqueue(call);
        }
        
        List<Call> highPriorityCalls = new ArrayList<>();
        while (!highPriorityQueue.isEmpty()) {
            Call call = highPriorityQueue.removeFirst();
            // Only check abandonment for calls that have arrived
            if (currentTime >= call.getTimestamp() && call.shouldAbandon()) {
                missedCalls.add(call);
                System.out.println("Call " + call.getCustomerId() + " ABANDONED after " + call.getWaitTime() + " time units");
            } else {
                highPriorityCalls.add(call);
            }
        }
        for (Call call : highPriorityCalls) {
            highPriorityQueue.addLast(call);
        }
    }
    
    private void assignCallsToAgents() {
        for (Agent agent : agents) {
            if (!agent.isBusy()) {
                Call call = null;
                
                // Check High Priority Queue for available calls
                if (!highPriorityQueue.isEmpty()) {
                    List<Call> tempCalls = new ArrayList<>();
                    Call availableCall = null;
                    
                    while (!highPriorityQueue.isEmpty()) {
                        Call tempCall = highPriorityQueue.removeFirst();
                        if (currentTime >= tempCall.getTimestamp() && availableCall == null) {
                            availableCall = tempCall;
                        } else {
                            tempCalls.add(tempCall);
                        }
                    }
                    
                    // Put back unavailable calls
                    for (Call tempCall : tempCalls) {
                        highPriorityQueue.addLast(tempCall);
                    }
                    
                    call = availableCall;
                } 
                // Check General Queue if no high priority call found
                else if (!generalQueue.isEmpty()) {
                    List<Call> tempCalls = new ArrayList<>();
                    Call availableCall = null;
                    
                    while (!generalQueue.isEmpty()) {
                        Call tempCall = generalQueue.dequeue();
                        if (currentTime >= tempCall.getTimestamp() && availableCall == null) {
                            availableCall = tempCall;
                        } else {
                            tempCalls.add(tempCall);
                        }
                    }
                    
                    // Put back unavailable calls
                    for (Call tempCall : tempCalls) {
                        generalQueue.enqueue(tempCall);
                    }
                    
                    call = availableCall;
                }
                
                if (call != null) {
                    int callDuration = 8; // Fixed 8 units for both min and max
                    agent.assignCall(call, callDuration);
                    System.out.println("Agent " + agent.getId() + " assigned to " + call.getCustomerId() + " (" + call.getType() + ") for " + callDuration + " time units");
                }
            }
        }
    }
    
    private void printTickSummary() {
        List<Agent> busyAgents = new ArrayList<>();
        for (Agent agent : agents) {
            if (agent.isBusy()) {
                busyAgents.add(agent);
            }
        }
        
        System.out.println("Busy Agents: " + busyAgents);
    }
    
    public void showStatus() {
        System.out.println("\n=== CALL CENTER STATUS (Time: " + currentTime + ") ===");
        System.out.println("High Priority Queue: " + highPriorityQueue);
        System.out.println("General Queue: " + generalQueue);
        
        List<Agent> busyAgents = new ArrayList<>();
        for (Agent agent : agents) {
            if (agent.isBusy()) {
                busyAgents.add(agent);
            }
        }
        System.out.println("Busy Agents: " + busyAgents);
        System.out.println("Missed Calls: " + missedCalls.size());
        if (!missedCalls.isEmpty()) {
            System.out.print("Missed: ");
            for (int i = 0; i < missedCalls.size(); i++) {
                if (i > 0) System.out.print(", ");
                System.out.print(missedCalls.get(i).getCustomerId());
            }
            System.out.println();
        }
        System.out.println("=====================================\n");
    }
    
    public int getCurrentTime() {
        return currentTime;
    }
}
