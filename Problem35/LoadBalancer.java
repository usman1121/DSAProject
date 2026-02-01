import java.util.Scanner;

class ServerNode {
    String name;
    boolean alive;
    ServerNode next;

    ServerNode(String name) {
        this.name = name;
        this.alive = true;
        this.next = null;
    }
}

public class LoadBalancer {
    private ServerNode head = null;
    private ServerNode current = null;

    // ADD_SERVER <ip>
    public void addServer(String name) {
        ServerNode newNode = new ServerNode(name);

        if (head == null) {
            head = newNode;
            newNode.next = head;
            current = head;
        } else {
            ServerNode temp = head;
            while (temp.next != head) {
                temp = temp.next;
            }
            temp.next = newNode;
            newNode.next = head;
        }
        System.out.println("Added " + name);
    }

    // KILL_SERVER <ip>
    public void killServer(String name) {
        if (head == null) return;

        ServerNode temp = head;
        do {
            if (temp.name.equals(name)) {
                temp.alive = false;
                System.out.println("Marked " + name + " dead.");
                return;
            }
            temp = temp.next;
        } while (temp != head);

        System.out.println("Server not found.");
    }

    // REVIVE_SERVER <ip>
    public void reviveServer(String name) {
        if (head == null) return;

        ServerNode temp = head;
        do {
            if (temp.name.equals(name)) {
                temp.alive = true;
                System.out.println("Revived " + name);
                return;
            }
            temp = temp.next;
        } while (temp != head);

        System.out.println("Server not found.");
    }

    // DISPATCH_REQUEST
    public void dispatch() {
        if (current == null) {
            System.out.println("No servers available.");
            return;
        }

        ServerNode start = current;
        do {
            if (current.alive) {
                System.out.println("To " + current.name);
                current = current.next;
                return;
            }
            current = current.next;
        } while (current != start);

        System.out.println("No alive servers to handle request.");
    }

    // MAIN with CLI
    public static void main(String[] args) {
        LoadBalancer lb = new LoadBalancer();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Fault-Tolerant Load Balancer");
        System.out.println("Commands: ADD_SERVER <name>, KILL_SERVER <name>, REVIVE_SERVER <name>, DISPATCH_REQUEST, EXIT");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();
            String[] parts = input.split(" ");

            if (parts[0].equalsIgnoreCase("ADD_SERVER")) {
                lb.addServer(parts[1]);
            } 
            else if (parts[0].equalsIgnoreCase("KILL_SERVER")) {
                lb.killServer(parts[1]);
            } 
            else if (parts[0].equalsIgnoreCase("REVIVE_SERVER")) {
                lb.reviveServer(parts[1]);
            } 
            else if (parts[0].equalsIgnoreCase("DISPATCH_REQUEST")) {
                lb.dispatch();
            } 
            else if (parts[0].equalsIgnoreCase("EXIT")) {
                System.out.println("Exiting...");
                break;
            } 
            else {
                System.out.println("Invalid command.");
            }
        }

        scanner.close();
    }
}
