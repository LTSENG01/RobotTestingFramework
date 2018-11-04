package ltseng01.testableframework;

/**
 *
 */
public class TestResult {

    private String mName = "";
    private String mResult = "";
    private String mExpected = "";
    private String mActual = "";
    private Double mTime = 0.0;

    public TestResult() {

    }

    /**
     *
     *
     * @param name
     * @param result
     * @param expected
     * @param actual
     */
    public TestResult setTestResults(String name, Result result, String expected, String actual) {
        this.mName = name;
        this.mResult = result.toString();
        this.mExpected = expected;
        this.mActual = actual;

        return this;
    }

    public void setTestTime(Double time) {
        this.mTime = time;
    }

    /**
     *
     *
     * @return
     */
    public String[] getTestResults() {
        return new String[]{ mName, mResult, mExpected, mActual, mTime.toString() };
    }

}
