package testable;

public class TestData {

    private boolean pass;
    private double value;
    private double timeToComplete;

    public TestData(boolean pass, double value, double timeToComplete) {
        this.pass = pass;
        this.value = value;
        this.timeToComplete = timeToComplete;
    }

    public boolean isPassed() {
        return pass;
    }

    public double getValue() {
        return value;
    }

    public double getTimeToComplete() {
        return timeToComplete;
    }

}
