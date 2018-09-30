package larrytseng.testableframework.code;

import larrytseng.testableframework.testables.*;
import larrytseng.testableframework.testables.TestManager.TestableTypes;

public class ExampleObject implements StaticTestable, DynamicTestable, ControlsTestable {

    private final int temperature = 10;

    public ExampleObject() {
        TestManager.registerTestable("ExampleObject-Temperature", this, TestableTypes.STATIC);
        TestManager.registerTestable("ExampleObject-ButtonA", this, TestableTypes.CONTROLS);
    }

    @Override
    public TestData controlsTest() throws InterruptedException {
        // Print Waiting Message in Console
        System.out.println("Press Button A");
        // Activate Button
        Thread.sleep(1000);
        return new TestData("ExampleObject-ButtonA", true, 1);
    }

    @Override
    public TestData dynamicTest() {
        return null;
    }

    @Override
    public TestData staticTest() throws InterruptedException {
        return new TestData("ExampleObject-Temperature", temperature > 5, temperature);
    }

}
