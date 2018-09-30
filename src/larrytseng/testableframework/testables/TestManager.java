package larrytseng.testableframework.testables;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class TestManager {

    private static volatile boolean readyToTest = false;
    private static boolean currentlyTesting = false;

    private static Thread testingThread;

    static {
        staticTestables = new HashMap<>();
        dynamicTestables = new HashMap<>();
        controlsTestables = new HashMap<>();
    }

    // collection of Testable objects
    private static HashMap<StaticTestable, TestData> staticTestables;
    private static HashMap<DynamicTestable, TestData> dynamicTestables;
    private static HashMap<ControlsTestable, TestData> controlsTestables;

    public static void readyForTesting() {
        displayTests();
    }

    private static void displayTests() {

        Gson gson = new Gson();

        String statics = gson.toJson(staticTestables.values());
        String dynamics = gson.toJson(dynamicTestables.values());
        String controls = gson.toJson(controlsTestables.values());

        System.out.println(statics + dynamics + controls);

        try {
            File file = new File("src/app/testdata.json");

            if (file.exists())
                file.delete();

            FileWriter fw = new FileWriter(file, false);
            fw.write("{ \"static\": ");
            fw.write(statics);
            fw.write(", \"dynamic\": ");
            fw.write(dynamics);
            fw.write(", \"controls\": ");
            fw.write(controls);
            fw.write("}");
            fw.close();

            file.renameTo(new File("src/app/testdata.json"));

        } catch (IOException e) {
            System.err.println("Error creating/saving testdata.json");
            e.printStackTrace();
        }

    }

    public static void prepareToRunTests(String testableNames) {

        if (currentlyTesting)
            return;

        // Json 2D array of names, comma delimited --> String[][]
        Gson gson = new Gson();
        String[][] testArrays = gson.fromJson(testableNames, String[][].class);

        // Update the testable's run values in testdata.json
        for (String test : testArrays[0]) {
            System.out.println("Deserialized static test: " + test);

            staticTestables.values().forEach((value) -> {
                if (value.getName().equals(test)) {
                    value.setTest(true);
                }
            });

        }

        for (String test : testArrays[1]) {
            System.out.println("Deserialized dynamic test: " + test);

            dynamicTestables.values().forEach((value) -> {
                if (value.getName().equals(test)) {
                    value.setTest(true);
                }
            });

        }

        for (String test : testArrays[2]) {
            System.out.println("Deserialized controls test: " + test);

            controlsTestables.values().forEach((value) -> {
                if (value.getName().equals(test)) {
                    value.setTest(true);
                }
            });

        }

        // publish new TestData
        displayTests();
        readyToTest = true;

        // TestPeriodic (doesn't need the extra thread?)
        new Thread(TestManager::runTests).start();

    }

    public static void runTests() {

        if (!readyToTest)
            return;

        currentlyTesting = true;

        testingThread = new Thread(() -> {

            /*

            Uses the Java SE System class to keep track of the time that each test takes.
            Ideally, one would use the FPGA timer in the RoboRIO, but I want to keep this framework
            independent of WPILib. If someone can implement it so that either one can be used depending on the
            available hardware, go for it.

             */

            staticTestables.forEach((test, data) -> {
                if (!testingThread.isInterrupted()) {
                    if (data.getTest() && data.getTimeToComplete() == 0.0) {
                        try {
                            double startTime = System.currentTimeMillis();
                            TestData result = test.staticTest();
                            Thread.sleep(20);
                            result.setTimeToComplete(((System.currentTimeMillis() - startTime)/1000.0));
                            staticTestables.replace(test, result);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    displayTests();
                }

            });

            dynamicTestables.forEach((test, data) -> {
                if (!testingThread.isInterrupted()) {
                    if (data.getTest() && data.getTimeToComplete() == 0.0) {
                        try {
                            double startTime = System.currentTimeMillis();
                            TestData result = test.dynamicTest();
                            Thread.sleep(20);
                            result.setTimeToComplete(((System.currentTimeMillis() - startTime)/1000.0));
                            dynamicTestables.replace(test, result);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    displayTests();
                }

            });

            controlsTestables.forEach((test, data) -> {
                if (!testingThread.isInterrupted()) {
                    if (data.getTest() && data.getTimeToComplete() == 0.0) {
                        try {
                            double startTime = System.currentTimeMillis();
                            TestData result = test.controlsTest();
                            Thread.sleep(20);
                            result.setTimeToComplete(((System.currentTimeMillis() - startTime)/1000.0));
                            controlsTestables.replace(test, result);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    displayTests();
                }

            });

            System.out.println("Testing has finished!");

        });

        testingThread.start();

    }

    public enum TestableTypes {
        STATIC, DYNAMIC, CONTROLS
    }

    public static void registerTestable(String name, Testable testable, TestableTypes type) {
        if (type == TestableTypes.STATIC)
            staticTestables.put((StaticTestable) testable, new TestData(name));
        else if (type == TestableTypes.DYNAMIC)
            dynamicTestables.put((DynamicTestable) testable, new TestData(name));
        else if (type == TestableTypes.CONTROLS)
            controlsTestables.put((ControlsTestable) testable, new TestData(name));
    }

    public static void stopTesting() {
        System.out.println("Stopping testing!");
        testingThread.interrupt();
        currentlyTesting = false;
        // WebServer.stop(); // move to robotDisabled or autoInit
    }

}
