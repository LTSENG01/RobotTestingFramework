package ltseng01.testableframework;


public class Main {
//
//    private static ExampleObject exampleObject;
    private static ExampleObjectB exampleObjectB;


    public static void main(String[] args) {

        NetworkTablesCommunication nt = new NetworkTablesCommunication();
//        exampleObject = new ExampleObject();
        exampleObjectB = new ExampleObjectB("1");

        TestManager.displayTests();

        TestManager.runTests();

        waiter();
    }

    public static void waiter() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        waiter();
    }

}
