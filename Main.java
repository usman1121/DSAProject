import java.util.Scanner;

public class Main {
    private static CallCenter callCenter;
    private static Scanner scanner;
    
    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        callCenter = new CallCenter(100);
        
        System.out.println("=== INTELLIGENT CALL CENTER DIRECTOR ===");
        System.out.println("Available commands:");
        System.out.println("  CALL <id> <type> <time> - Register a new call");
        System.out.println("  ASSIGN_AGENTS <n> - Set number of agents");
        System.out.println("  TICK - Advance time by 1 unit");
        System.out.println("  STATUS - Show current status");
        System.out.println("  SAMPLE - Run sample execution");
        System.out.println("  EXIT - Exit the program");
        System.out.println("=====================================");
        
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            
            if (input.equalsIgnoreCase("EXIT")) {
                break;
            } else if (input.equalsIgnoreCase("SAMPLE")) {
                runSampleExecution();
            } else if (input.equalsIgnoreCase("STATUS")) {
                callCenter.showStatus();
            } else if (input.equalsIgnoreCase("TICK")) {
                callCenter.tick();
            } else if (input.startsWith("ASSIGN_AGENTS")) {
                handleAssignAgents(input);
            } else if (input.startsWith("CALL")) {
                handleCall(input);
            } else if (!input.isEmpty()) {
                System.out.println("Unknown command. Try: CALL, ASSIGN_AGENTS, TICK, STATUS, SAMPLE, EXIT");
            }
        }
        
        System.out.println("Goodbye!");
        scanner.close();
    }
    
    private static void handleAssignAgents(String input) {
        try {
            String[] parts = input.split(" ");
            if (parts.length != 2) {
                System.out.println("Usage: ASSIGN_AGENTS <number>");
                return;
            }
            int n = Integer.parseInt(parts[1]);
            callCenter.assignAgents(n);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number. Usage: ASSIGN_AGENTS <number>");
        }
    }
    
    private static void handleCall(String input) {
        try {
            String[] parts = input.split(" ");
            if (parts.length != 4) {
                System.out.println("Usage: CALL <id> <type> <time>");
                return;
            }
            String customerId = parts[1];
            String type = parts[2];
            int timestamp = Integer.parseInt(parts[3]);
            
            if (!type.equals("General") && !type.equals("Tech")) {
                System.out.println("Type must be 'General' or 'Tech'");
                return;
            }
            
            callCenter.receiveCall(customerId, type, timestamp);
        } catch (NumberFormatException e) {
            System.out.println("Invalid time. Usage: CALL <id> <type> <time>");
        }
    }
    
    private static void runSampleExecution() {
        System.out.println("\n=== RUNNING SAMPLE EXECUTION ===");
        
        callCenter = new CallCenter(100);
        
        System.out.println("Setting up call center with 2 agents...");
        callCenter.assignAgents(2);
        
        System.out.println("\nReceiving initial calls at time 0...");
        callCenter.receiveCall("C1", "General", 0);
        callCenter.receiveCall("C2", "Tech", 0);
        
        System.out.println("\nProcessing first tick (Time 0 -> 1)...");
        callCenter.tick();
        
        System.out.println("\nReceiving more calls...");
        callCenter.receiveCall("C3", "General", 2);
        callCenter.receiveCall("C4", "General", 3);
        
        System.out.println("\nSimulating time passing...");
        for (int i = 2; i <= 11; i++) {
            callCenter.tick();
            if (i == 5) {
                callCenter.receiveCall("C5", "Tech", 5);
            }
        }
        
        System.out.println("\nFinal status after escalation:");
        callCenter.showStatus();
        
        System.out.println("=== SAMPLE EXECUTION COMPLETE ===\n");
    }
}
