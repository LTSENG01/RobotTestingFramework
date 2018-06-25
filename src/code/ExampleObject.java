package code;

import testable.*;

public class ExampleObject implements StaticTestable, DynamicTestable, ControlsTestable {

    private final int temperature = 10;

    public ExampleObject() {
        TestManager.registerTestable("ExampleObject - Temperature", this);
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
        return new TestData(temperature > 5, temperature, 0.5);
    }

}
