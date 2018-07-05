package code;

import testable.*;

public class ExampleObjectB implements StaticTestable, DynamicTestable, ControlsTestable {

    private final int temperature = 10;

    // No spaces allowed in the name
    public ExampleObjectB() {
        TestManager.registerTestable("ExampleObjectB-Temperature", this);
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
    public TestData staticTest() {
        return new TestData("ExampleObjectB-Temperature", temperature > 5, temperature, 0.5);
    }

}
