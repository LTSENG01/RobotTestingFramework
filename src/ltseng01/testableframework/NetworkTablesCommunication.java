package ltseng01.testableframework;

import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

import java.util.HashMap;

public class NetworkTablesCommunication {

    private static NetworkTableInstance inst;
    private static NetworkTable mainTable;
    private static NetworkTable statusTable;
    private static NetworkTable finishedTable;

    public NetworkTablesCommunication() {

        // Network Tables Setup

        inst = NetworkTableInstance.getDefault();
        mainTable = inst.getTable("RobotTestingFramework");
        statusTable = mainTable.getSubTable("status");
        finishedTable = mainTable.getSubTable("finishedTests");

        inst.startClient("localhost");  // No need on an actual robot

        mainTable.getEntry("availableTests").setStringArray(new String[]{"test1"});
        statusTable.getEntry("running").setBoolean(false);
        finishedTable.getEntry("test1").setStringArray(new String[]{"Test1", "Result"});

        statusTable.getEntry("running").addListener(event -> {
            if (event.value.getBoolean()) {
                TestManager.runTests();
            } else {
                TestManager.interruptTesting();
            }
        }, EntryListenerFlags.kUpdate | EntryListenerFlags.kNew);

    }

    public static void publishTestInfo(String[] tests) {
        mainTable.getEntry("availableTests").setStringArray(tests);
        mainTable.getEntry("testsToRun").setStringArray(tests);     // TODO: Remove (temp for testing)
    }

    public static String[] receiveTestsToRun() {
        return mainTable.getEntry("testsToRun").getStringArray(new String[]{});
    }

    public static void publishTestResult(HashMap<String, TestResult> testResults) {
        testResults.forEach((name, testResult) -> finishedTable
                        .getEntry(name)
                        .setStringArray(testResult.getTestResults()));
    }

    public static void transmitInstructions(String instructions) {
        statusTable.getEntry("instructions").setString(instructions);
    }

}
