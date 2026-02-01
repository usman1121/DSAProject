

class Tab {
    String title;
    MyArrayList<String> history;
    int current;

    public Tab(String title) {
        this.title = title;
        this.history = new MyArrayList<>();
        this.current = -1;
    }
}

public class BrowserEngine {
    private static final int MAX_HISTORY_SIZE = 50;

    private MyDoublyLinkedList<Tab> openTabs;
    private MyStack<Tab> closedTabs;
    private MyDoublyLinkedList<String> tabUsage;
    private String activeTabTitle;

    public BrowserEngine() {
        openTabs = new MyDoublyLinkedList<>();
        closedTabs = new MyStack<>();
        tabUsage = new MyDoublyLinkedList<>();
        activeTabTitle = null;
    }

    private void updateUsage(String title) {
        tabUsage.remove(title);
        tabUsage.addFirst(title);
    }

    private Tab findTab(String title) {
        for (int i = 0; i < openTabs.size(); i++) {
            Tab t = openTabs.get(i);
            if (t.title.equals(title)) return t;
        }
        return null;
    }

    public void newTab(String title) {
        if (findTab(title) != null) {
            System.out.println("Tab '" + title + "' already exists.");
            return;
        }
        Tab newTab = new Tab(title);
        openTabs.add(newTab);
        activeTabTitle = title;
        updateUsage(title);
        System.out.println("Tab '" + title + "' created. Active. History: " + newTab.history + ".");
    }

    public void switchTab(String title) {
        if (findTab(title) == null) {
            System.out.println("Tab '" + title + "' does not exist.");
            return;
        }
        activeTabTitle = title;
        updateUsage(title);
        Tab tab = findTab(title);
        String currentUrl = tab.current >= 0 ? tab.history.get(tab.current) : "None";
        System.out.println("Switched to '" + title + "'. Current: " + currentUrl + ". History: " + tab.history + ".");
    }

    public void visit(String url) {
        if (activeTabTitle == null) {
            System.out.println("No active tab.");
            return;
        }
        Tab tab = findTab(activeTabTitle);
        int existingIndex = tab.history.indexOf(url);
        if (existingIndex != -1) {
            // URL already exists, set current to it and clear forward
            tab.current = existingIndex;
            tab.history.removeFrom(tab.current + 1);
        } else {
            // Clear forward history and add new URL
            tab.history.removeFrom(tab.current + 1);
            tab.history.add(url);
            tab.current++;
        }
        updateUsage(activeTabTitle);
        System.out.println("Current: " + url + ". History: " + tab.history + ".");
        checkMemory();
    }

    private void checkMemory() {
        int total = 0;
        for (int i = 0; i < openTabs.size(); i++) {
            Tab t = openTabs.get(i);
            total += t.history.size();
        }
        while (total > MAX_HISTORY_SIZE) {
            evict();
            total--;
        }
    }

    private void evict() {
        for (int i = tabUsage.size() - 1; i >= 0; i--) {
            String title = tabUsage.get(i);
            if (!title.equals(activeTabTitle)) {
                Tab tab = findTab(title);
                if (tab.history.size() > 1) {
                    tab.history.remove(0);
                    tab.current--;
                    return;
                }
            }
        }
    }

    public void back() {
        if (activeTabTitle == null) return;
        Tab tab = findTab(activeTabTitle);
        if (tab.current > 0) {
            tab.current--;
            System.out.println("Current: " + tab.history.get(tab.current));
        } else {
            System.out.println("Cannot go back.");
        }
    }

    public void forward() {
        if (activeTabTitle == null) return;
        Tab tab = findTab(activeTabTitle);
        if (tab.current < tab.history.size() - 1) {
            tab.current++;
            System.out.println("Current: " + tab.history.get(tab.current));
        } else {
            System.out.println("Cannot go forward.");
        }
    }

    public void closeTab() {
        if (activeTabTitle == null) {
            System.out.println("No active tab to close.");
            return;
        }
        Tab tab = findTab(activeTabTitle);
        openTabs.remove(tab);
        closedTabs.push(tab);
        tabUsage.remove(activeTabTitle);
        if (!openTabs.isEmpty()) {
            activeTabTitle = openTabs.getLast().title;
        } else {
            activeTabTitle = null;
        }
        System.out.println("Closed '" + tab.title + "'. Stored in Closed History.");
        if (activeTabTitle != null) {
            System.out.println("Active is now '" + activeTabTitle + "'.");
        }
    }

    public void restoreTab() {
        if (closedTabs.isEmpty()) {
            System.out.println("No closed tabs to restore.");
            return;
        }
        Tab tab = closedTabs.pop();
        openTabs.add(tab);
        activeTabTitle = tab.title;
        updateUsage(tab.title);
        System.out.println("Restored '" + tab.title + "'. Active.");
        System.out.println("History Preserved: " + tab.history + ".");
    }

    public void status() {
        System.out.println("Open Tabs:");
        for (int i = 0; i < openTabs.size(); i++) {
            Tab t = openTabs.get(i);
            System.out.println("- " + t.title + (t.title.equals(activeTabTitle) ? " (active)" : ""));
        }
        int total = 0;
        for (int i = 0; i < openTabs.size(); i++) {
            Tab t = openTabs.get(i);
            total += t.history.size();
        }
        System.out.println("Total memory usage: " + total + "/" + MAX_HISTORY_SIZE);
    }

}