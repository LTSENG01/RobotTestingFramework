package larrytseng.testableframework.testables;

public interface DynamicTestable extends Testable {

    TestData dynamicTest() throws InterruptedException;

}
