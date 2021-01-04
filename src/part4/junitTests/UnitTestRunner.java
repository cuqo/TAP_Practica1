package part4.junitTests;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import part1.junitTests.MailStoreUnitTest;
import part1.junitTests.MailSystemUnitTest;
import part1.junitTests.MailboxUnitTest;

import java.util.ArrayList;
import java.util.List;

public class UnitTestRunner {

    public static void main(String[] args) {
        List<Result> results = new ArrayList<>();
        results.add(JUnitCore.runClasses(MailStoreUnitTest.class));
        results.add(JUnitCore.runClasses(MailboxUnitTest.class));
        results.add(JUnitCore.runClasses(MailSystemUnitTest.class));

        results.forEach(result -> {
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
            System.out.println(result.wasSuccessful());
        });
    }
}
