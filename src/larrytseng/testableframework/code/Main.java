package larrytseng.testableframework.code;

import larrytseng.testableframework.testables.TestManager;

public class Main {

    private static ExampleObject exampleObject;
    private static ExampleObjectB exampleObjectB;


    public static void main(String[] args) {

        exampleObject = new ExampleObject();
        exampleObjectB = new ExampleObjectB();

        NetworkTablesCommunication ntc = new NetworkTablesCommunication();

        // Throw in TestInit()
        TestManager.readyForTesting("src/larrytseng/testableframework/");

        // Throw in disabled?
        // TestManager.stopTesting();
    }

}
