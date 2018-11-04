package ltseng01.testableframework;

import java.util.HashMap;

public class TestManager {

    private static HashMap<String, Testable> testCollection;

    private static Thread testingThread;

    static {
        testCollection = new HashMap<>();
    }

    public static void registerTest(String name, Testable testObject) {
        testCollection.put(name, testObject);
    }

    public static void displayTests() {
        String[] tests = (String[]) testCollection.entrySet().toArray();
        NetworkTablesCommunication.publishTestInfo(tests);
    }



}
