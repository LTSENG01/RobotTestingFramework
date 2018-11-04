package ltseng01.testableframework;

import ltseng01.testableframework.code.ExampleObject;

public class Main {

    private static ExampleObject exampleObject;
    private static ExampleObjectB exampleObjectB;


    public static void main(String[] args) {

        exampleObject = new ExampleObject();
        exampleObjectB = new ExampleObjectB();

        NetworkTablesCommunication nt = new NetworkTablesCommunication();


    }

}
