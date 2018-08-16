package code;

import testable.*;

import java.time.LocalDateTime;

public class ExampleObject implements StaticTestable, DynamicTestable, ControlsTestable {

    private final int temperature = 10;

    public ExampleObject() {
        TestManager.registerTestable("ExampleObject-Temperature", this);
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
        return new TestData("ExampleObject-Temperature", temperature > 5, temperature);
    }

}
