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

    public NetworkTablesCommunication(boolean onRobot) {

        inst = NetworkTableInstance.getDefault();
        mainTable = inst.getTable("RobotTestingFramework");
        statusTable = mainTable.getSubTable("status");
        finishedTable = mainTable.getSubTable("finishedTests");

        // Start the client if not using an FRC robot
        if (!onRobot)
            inst.startClient("localhost");

        mainTable.getEntry("availableTests").setStringArray(new String[]{});
        statusTable.getEntry("running").setBoolean(false);

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
