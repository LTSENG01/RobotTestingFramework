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
     * @param mName
     * @param mResult
     * @param mExpected
     * @param mActual
     * @param mTime
     */
    public void setTestResults(String mName, String mResult, String mExpected, String mActual, Double mTime) {
        this.mName = mName;
        this.mResult = mResult;
        this.mExpected = mExpected;
        this.mActual = mActual;
        this.mTime = mTime;
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
