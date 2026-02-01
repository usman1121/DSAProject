public class Agent {
    private int id;
    private boolean isBusy;
    private Call currentCall;
    private int remainingCallTime;
    
    public Agent(int id) {
        this.id = id;
        this.isBusy = false;
        this.currentCall = null;
        this.remainingCallTime = 0;
    }
    
    public int getId() {
        return id;
    }
    
    public boolean isBusy() {
        return isBusy;
    }
    
    public Call getCurrentCall() {
        return currentCall;
    }
    
    public int getRemainingCallTime() {
        return remainingCallTime;
    }
    
    public void assignCall(Call call, int callDuration) {
        this.currentCall = call;
        this.remainingCallTime = callDuration;
        this.isBusy = true;
    }
    
    public void processCall() {
        if (isBusy && remainingCallTime > 0) {
            remainingCallTime--;
            if (remainingCallTime == 0) {
                completeCall();
            }
        }
    }
    
    public void completeCall() {
        this.currentCall = null;
        this.isBusy = false;
        this.remainingCallTime = 0;
    }
    
    @Override
    public String toString() {
        if (isBusy) {
            return "Ag" + id + ": " + currentCall.getCustomerId() + " (" + remainingCallTime + "s)";
        } else {
            return "Ag" + id + ": Free";
        }
    }
}
