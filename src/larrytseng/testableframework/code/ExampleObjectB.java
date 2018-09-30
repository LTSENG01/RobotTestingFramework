package larrytseng.testableframework.code;

import larrytseng.testableframework.testables.*;
import larrytseng.testableframework.testables.TestManager.TestableTypes;

public class ExampleObjectB implements StaticTestable, DynamicTestable, ControlsTestable {

    private final int temperature = 2;

    // No spaces allowed in the name
    public ExampleObjectB() {
        TestManager.registerTestable("ExampleObjectB-Temperature", this, TestableTypes.STATIC);
    }

    @Override
    public TestData controlsTest() {
        return null;
    }

    @Override
    public TestData dynamicTest() {
        return null;
    }

    @Override
    public TestData staticTest() throws InterruptedException {
        return new TestData("ExampleObjectB-Temperature", temperature > 5, temperature);
    }

}
