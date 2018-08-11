package testable;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class TestManager {

    static {
        staticTestables = new HashMap<>();
        dynamicTestables = new HashMap<>();
        controlsTestables = new HashMap<>();
    }

    // collection of Testable objects
    private static HashMap<TestData, StaticTestable> staticTestables;
    private static HashMap<TestData, DynamicTestable> dynamicTestables;
    private static HashMap<TestData, ControlsTestable> controlsTestables;

    public static void readyForTesting() {

        displayTests();

        // Do something - update #status to READY


    }

    private static void displayTests() {

        Gson gson = new Gson();

        String statics = gson.toJson(staticTestables.keySet());
        String dynamics = gson.toJson(dynamicTestables.keySet());
        String controls = gson.toJson(controlsTestables.keySet());

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

    public static void runTests(String testableNames) {
        // Json array of names, comma delimited --> String[]
        Gson gson = new Gson();
        String[] testNames = gson.fromJson(testableNames, String[].class);

        for (String test : testNames) {
            System.out.println("Deserialized: " + test);
        }

        // Lookup the testable associated with the string (edit Gson/JSON in a friendly way?)

        // Update the testable's run values in testdata.json

        // trigger update?

    }

    private static void publishResult(String name, TestData testData) {
        // Do something
    }

    public static void registerTestable(String name, Testable testable) {
        if (testable instanceof StaticTestable)
            staticTestables.put(new TestData(name), (StaticTestable) testable);
        else if (testable instanceof DynamicTestable)
            dynamicTestables.put(new TestData(name), (DynamicTestable) testable);
        else if (testable instanceof ControlsTestable)
            controlsTestables.put(new TestData(name), (ControlsTestable) testable);
    }

}
