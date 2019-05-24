package base;

import objparser.TestUtils;
import org.junit.*;
import org.junit.rules.TestName;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BaseTest {

    @Rule
    public TestWatcher watcher = new TestWatcher() {
        @Override
        protected void succeeded(Description description) {
            TestUtils.success();
        }
    };
    @Rule
    public final TestName name = new TestName();

    @BeforeClass
    public static void beforeClass() {

    }

    @Before
    public void beforeTest() {
        String className = this.getClass().getName();
        List<String> tokens = Arrays.asList(className.split("\\."));
        String correctedClassName = tokens.stream()
                .filter(token -> Character.isUpperCase(token.charAt(0)))
                .collect(Collectors.joining("."));

        System.out.println("Starting test - \033[34m" + correctedClassName + "\033[0m#\033[34m" + name.getMethodName() + "()\033[0m");
    }

    @After
    public void afterTest() {

    }

    @AfterClass
    public static void afterClass() {

    }
}
