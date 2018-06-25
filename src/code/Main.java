package code;

import testable.TestManager;

public class Main {

    private static ExampleObject exampleObject;

    public static void main(String[] args) {

        WebServer.start();

        exampleObject = new ExampleObject();

        TestManager.readyForTesting();

    }

}
