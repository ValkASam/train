package junit;

import org.junit.*;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

/**
 * Created by Valk on 21.01.16.
 */
public class JUnitTest {
    public static void main(String[] args) throws Exception {
        JUnitCore runner = new JUnitCore();
        Result result = runner.run(MathFuncTest.class);
        System.out.println("run tests: " + result.getRunCount());
        System.out.println("failed tests: " + result.getFailureCount());
        System.out.println("ignored tests: " + result.getIgnoreCount());
        System.out.println("success: " + result.wasSuccessful());
    }
}

class MathFuncTest {
    private MathFunc math;

    @Before
    public void init() { math = new MathFunc(); }
    @After
    public void tearDown() { math = null; }

    @Test
    public void calls() {
        Assert.assertEquals(0, math.getCalls());

        math.factorial(1);
        Assert.assertEquals(1, math.getCalls());

        math.factorial(1);
        Assert.assertEquals(2, math.getCalls());
    }

    @Test
    public void factorial() {
        Assert.assertTrue(math.factorial(0) == 1);
        Assert.assertTrue(math.factorial(1) == 1);
        Assert.assertTrue(math.factorial(5) == 120);
    }

    @Test(expected = IllegalArgumentException.class)
    public void factorialNegative() {
        math.factorial(-1);
    }

    @Ignore
    @Test
    public void todo() {
        Assert.assertTrue(math.plus(1, 1) == 3);
    }
}

class MathFunc {
    int calls;

    public int getCalls() {
        return calls;
    }

    public long factorial(int number) {
        calls++;

        if (number < 0)
            throw new IllegalArgumentException();

        long result = 1;
        if (number > 1) {
            for (int i = 1; i <= number; i++)
                result = result * i;
        }

        return result;
    }

    public long plus(int num1, int num2) {
        calls++;
        return num1 + num2;
    }
}
