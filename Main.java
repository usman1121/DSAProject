import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BrowserEngine engine = new BrowserEngine();
        Scanner sc = new Scanner(System.in);
        System.out.println("------------------------------------ The Modern Browser Engine --------------------------------- ");
        while (true) {
            printMenu();
            System.out.print("Choose an option: ");
            String choice = sc.nextLine().trim().toLowerCase();
            if (choice.isEmpty()) continue;
            char cmd = choice.charAt(0);
            switch (cmd) {
                case 'n':
                    System.out.print("Enter tab title: ");
                    String title = sc.nextLine().trim();
                    engine.newTab(title);
                    break;
                case 's':
                    System.out.print("Enter tab title: ");
                    title = sc.nextLine().trim();
                    engine.switchTab(title);
                    break;
                case 'v':
                    System.out.print("Enter URL: ");
                    String url = sc.nextLine().trim();
                    engine.visit(url);
                    break;
                case 'b':
                    engine.back();
                    break;
                case 'f':
                    engine.forward();
                    break;
                case 'c':
                    engine.closeTab();
                    break;
                case 'r':
                    engine.restoreTab();
                    break;
                case 't':
                    engine.status();
                    break;
                case 'x':
                    return;
                default:
                    System.out.println("Unknown option");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\nMenu:");
        System.out.println("n - open new tab");
        System.out.println("s - switch tab");
        System.out.println("v - visit a url");
        System.out.println("b - back");
        System.out.println("f - forward");
        System.out.println("c - close tab");
        System.out.println("r - restore tab");
        System.out.println("t - tab status");
        System.out.println("x - EXIT");
    }
}