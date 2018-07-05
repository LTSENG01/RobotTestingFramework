package testable;

/**
 * A data structure to represent the result of a test
 */
public class TestData {

    private String name;
    private Boolean pass;
    private Double value;
    private Double timeToComplete;

    public TestData(String name, boolean pass, double value, double timeToComplete) {
        this.name = name;
        this.pass = pass;
        this.value = value;
        this.timeToComplete = timeToComplete;
    }

    public TestData(String name) {
        this.name = name;
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

    public String getName() {
        return name;
    }
}
