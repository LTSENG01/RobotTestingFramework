package larrytseng.testableframework.code;

import larrytseng.testableframework.testables.TestManager;
import larrytseng.testableframework.testables.WebServer;

public class Main {

    private static ExampleObject exampleObject;
    private static ExampleObjectB exampleObjectB;

    public static void main(String[] args) {

        WebServer.start();

        exampleObject = new ExampleObject();
        exampleObjectB = new ExampleObjectB();

        // Throw in TestInit()
        TestManager.readyForTesting();

        // Throw in disabled?
        // TestManager.stopTesting();
    }

}
