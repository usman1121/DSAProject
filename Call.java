public class Call {
    private String customerId;
    private String type;
    private int timestamp;
    private int waitTime;
    private boolean escalated;
    
    public Call(String customerId, String type, int timestamp) {
        this.customerId = customerId;
        this.type = type;
        this.timestamp = timestamp;
        this.waitTime = 0;
        this.escalated = false;
    }
    
    public String getCustomerId() {
        return customerId;
    }
    
    public String getType() {
        return type;
    }
    
    public int getTimestamp() {
        return timestamp;
    }
    
    public int getWaitTime() {
        return waitTime;
    }
    
    public void incrementWaitTime() {
        waitTime++;
    }
    
    public boolean isEscalated() {
        return escalated;
    }
    
    public void escalate() {
        this.type = "Tech";
        this.escalated = true;
    }
    
    public boolean shouldEscalate() {
        return !escalated && type.equals("General") && waitTime >= 10;
    }
    
    public boolean shouldAbandon() {
        return waitTime >= 20;
    }
    
    @Override
    public String toString() {
        return customerId + (escalated ? "(escalated)" : "");
    }
}
