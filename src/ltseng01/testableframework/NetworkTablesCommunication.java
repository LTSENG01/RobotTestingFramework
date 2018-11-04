package ltseng01.testableframework;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class NetworkTablesCommunication {

    public NetworkTablesCommunication() {

        // Network Tables Setup
        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        NetworkTable mainTable = inst.getTable("RobotTestingFramework");
        NetworkTable statusTable = mainTable.getSubTable("status");
        NetworkTable finishedTable = mainTable.getSubTable("finishedTests");

        inst.startClient("localhost");

        //
        mainTable.getEntry("availableTests").setStringArray(new String[]{"test1"});
        statusTable.getEntry("running").setBoolean(false);
        finishedTable.getEntry("test1").setStringArray(new String[]{"Test1", "Pass"});

    }


    // listen to change in 'RobotTestingFramework/status/running'

    public static void publishTestInfo(String[] tests) {

    }

    public static void publishTestResult(TestResult result) {

    }

    public static void transmitInstructions(String instructions) {

    }

}
