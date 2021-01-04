package part2.junitTests;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.util.ArrayList;
import java.util.List;

public class UnitTestRunner {

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(FilterAndEncodingUnitTest.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println(result.wasSuccessful());

    }
}
