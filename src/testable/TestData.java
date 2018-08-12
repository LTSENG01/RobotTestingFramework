package testable;

/**
 * A data structure to represent the result of a test
 */
public class TestData {

    private String name;
    private Boolean test;
    private Boolean pass;
    private Double value;
    private Double time = 0.0;

    public TestData(String name, boolean pass, double value, double time) {
        this.name = name;
        this.pass = pass;
        this.value = value;
        this.time = time;
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
        return time;
    }

    public String getName() {
        return name;
    }

    public Boolean getTest() {
        return test;
    }

    public void setTest(Boolean test) {
        this.test = test;
    }

}
