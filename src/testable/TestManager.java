package testable;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class TestManager {

    private static boolean readyToTest = false;

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

        // Do something - update #status to READY


    }

    private static void displayTests() {

        Gson gson = new Gson();

        String statics = gson.toJson(staticTestables.values());
        String dynamics = gson.toJson(dynamicTestables.values());
        String controls = gson.toJson(controlsTestables.values());

        System.out.println(statics + dynamics + controls);

        try {
            File file = new File("src/app/testdata.json");

            if (file.exists()) {
                System.out.println("File already exists");
                if (file.delete())
                    System.out.println("Successful deletion");
            }

            FileWriter fw = new FileWriter(file, false);
            fw.write("{ \"static\": ");
            fw.write(statics);
            fw.write(", \"dynamic\": ");
            fw.write(dynamics);
            fw.write(", \"controls\": ");
            fw.write(controls);
            fw.write("}");
            fw.close();

            if (file.renameTo(new File("src/app/testdata.json")))
                System.out.println("Successful creation of testdata.json");

        } catch (IOException e) {
            System.err.println("Error creating/saving testdata.json");
            e.printStackTrace();
        }


    }

    public static void prepareToRunTests(String testableNames) {

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

        new Thread(TestManager::runTests).start();

    }

    public static void runTests() {
        if (!readyToTest) {
            return;
        }

        staticTestables.forEach((test, data) -> {
            if (data.getTest() && data.getTimeToComplete() == 0.0) {
                staticTestables.replace(test, test.staticTest());
            }
            displayTests();
        });

        dynamicTestables.forEach((test, data) -> {
            if (data.getTest() && data.getTimeToComplete() == 0.0) {
                dynamicTestables.replace(test, test.dynamicTest());
            }
            displayTests();
        });

        controlsTestables.forEach((test, data) -> {
            if (data.getTest() && data.getTimeToComplete() == 0.0) {
                controlsTestables.replace(test, test.controlsTest());
            }
            displayTests();
        });

    }

    private static void publishResult(String name, TestData testData) {
        // Do something
    }

    public static void registerTestable(String name, Testable testable) {
        if (testable instanceof StaticTestable)
            staticTestables.put((StaticTestable) testable, new TestData(name));
        else if (testable instanceof DynamicTestable)
            dynamicTestables.put((DynamicTestable) testable, new TestData(name));
        else if (testable instanceof ControlsTestable)
            controlsTestables.put((ControlsTestable) testable, new TestData(name));
    }

}
