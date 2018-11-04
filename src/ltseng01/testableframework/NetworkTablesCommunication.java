package ltseng01.testableframework;

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

        inst.startClient("localhost");

        mainTable.getEntry("availableTests").setStringArray(new String[]{"test1"});
        statusTable.getEntry("running").setBoolean(false);
        finishedTable.getEntry("test1").setStringArray(new String[]{"Test1", "Result"});

    }


    // listen to change in 'RobotTestingFramework/status/running'
    // start or stop TestProcedures

    public static void publishTestInfo(String[] tests) {

    }

    public static String[] receiveTestsToRun() {
        return null;
    }

    public static void publishTestResult(HashMap<String, TestResult> testResults) {
        testResults.forEach((name, testResult) -> {
            finishedTable.getEntry(name).setStringArray(testResult.getTestResults());
        });
    }

    public static void transmitInstructions(String instructions) {

    }

}
