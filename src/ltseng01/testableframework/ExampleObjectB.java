package ltseng01.testableframework;

public class ExampleObjectB {

    public ExampleObjectB(String name) {

        TestManager.registerTest("ExampleObjectB-" + name, this::testProcedure);

        TestManager.registerTest(name, () -> {

            Result result = Result.PASS;
            String expected = "10";
            String actual = "11";

            return new TestResult().setTestResults(name, result, expected, actual);
        });

    }

    public TestResult testProcedure() {
        Result result = Result.FAIL;
        String expected = "15";
        String actual = "14";

        return new TestResult().setTestResults("Hi", result, expected, actual);
    }


}
