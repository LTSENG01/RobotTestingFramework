package testable;

import com.google.gson.Gson;

import java.util.HashMap;

public class TestManager {

    static {
        staticTestables = new HashMap<>();
        dynamicTestables = new HashMap<>();
        controlsTestables = new HashMap<>();
    }

    // collection of Testable objects
    private static HashMap<String, StaticTestable> staticTestables;
    private static HashMap<String, DynamicTestable> dynamicTestables;
    private static HashMap<String, ControlsTestable> controlsTestables;

    public static void readyForTesting() {

        displayTests();

        // Do something - update #status to READY


    }

    private static void displayTests() {
        staticTestables.forEach((k, v) -> publishResult(k, v.staticTest()));
        dynamicTestables.forEach((k, v) -> publishResult(k, v.dynamicTest()));
        controlsTestables.forEach((k, v) -> publishResult(k, v.controlsTest()));

        Gson obj = new Gson();

    }

    public static void runTests(String[][] testableNames) {
        // 2D array format [3][x]
        //
        // [0] Static [3]: {"Battery", "Temperature", "Connection"}
        // [1] Dynamic [2]: {"Motor - FL", "Motor - BL"}
        // [2] Controls [3]: {"A", "B", "LT"}

    }

    private static void publishResult(String name, TestData testData) {
        // Do something
    }

    public static void registerTestable(String name, Testable testable) {
        if (testable instanceof StaticTestable)
            staticTestables.put(name, (StaticTestable) testable);
        else if (testable instanceof DynamicTestable)
            dynamicTestables.put(name, (DynamicTestable) testable);
        else if (testable instanceof ControlsTestable)
            controlsTestables.put(name, (ControlsTestable) testable);
    }

}
