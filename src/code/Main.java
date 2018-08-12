package code;

import testable.TestManager;

public class Main {

    private static ExampleObject exampleObject;
    private static ExampleObjectB exampleObjectB;

    public static void main(String[] args) {

        WebServer.start();

        exampleObject = new ExampleObject();
        exampleObjectB = new ExampleObjectB();

        // Throw in TestInit()
        TestManager.readyForTesting();

    }

}
