package ltseng01.testableframework;

import java.util.Arrays;
import java.util.HashMap;

public class TestManager {

    private static HashMap<String, Testable> testCollection;
    private static HashMap<String, Testable> testsToRun;
    private static HashMap<String, TestResult> finishedTests;

    private static Thread testingThread;

    static {
        testCollection = new HashMap<>();
        testsToRun = new HashMap<>();
        finishedTests = new HashMap<>();

        new NetworkTablesCommunication(false);
    }

    public static void registerTest(String name, Testable testObject) {
        testCollection.put(name, testObject);
    }

    public static void displayTests() {
        String[] tests = testCollection.keySet().toArray(new String[0]);
        Arrays.stream(tests).forEach(System.out::println);
        NetworkTablesCommunication.publishTestInfo(tests);
    }

    public static void runTests() {

        System.out.println("Beginning to run tests!");

        String[] tests = NetworkTablesCommunication.receiveTestsToRun();

        if (tests != null && tests.length > 0) {
            testCollection
                    .entrySet()
                    .stream()
                    .filter((entry) -> Arrays.stream(tests)
                            .anyMatch(n -> n.equals(entry.getKey())))
                    .forEach((entry) -> testsToRun.put(entry.getKey(), entry.getValue()));

        }

        testingThread = new Thread(() -> {
            testsToRun.forEach((name, testable) -> {
                if (!testingThread.isInterrupted()) {
                    double startTime = System.currentTimeMillis();
                    TestResult tr = testable.testProcedure();

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    tr.setTestTime(System.currentTimeMillis() - startTime);
                    finishedTests.put(name, tr);
                }

            });
        });

        testingThread.start();

        try {
            testingThread.join();
            System.out.println("Testing finished!");
            publishResults();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void publishResults() {
        NetworkTablesCommunication.publishTestResult(finishedTests);
    }

    public static void interruptTesting() {
        if (testingThread.isAlive()) {
            testingThread.interrupt();
            System.out.println("Testing interrupted!");
        }
    }


}
