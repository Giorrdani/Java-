import org.junit.*;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

@RunWith(Parameterized.class)
public class TestForTest {
    public static TestArray test;
    public int [] a;
    public int [] b;

    @BeforeClass
    public static void initTest(){
        test = new TestArray();
    }

    public TestForTest(int [] a, int [] b) {
        this.a = a;
        this.b = b;
    }

    @Parameterized.Parameters
    public static Iterable<Object[]> dataForTest() {
        return Arrays.asList(new Object[][]{
                {new int[]{5,6}, new int[]{1,2,3,4,5,6}},
                {new int[]{7}, new int[]{4,9,6,3,4,7}},
                {new int[]{5,9}, new int[]{2,3,4,5,9}},
                {new int[]{12,5}, new int[]{4,12,5}},
                {new int[]{5,6}, new int[]{1,2,3,4,5,6}},

        });
    }

    @Test
    public void arrTest2(){
        Assertions.assertArrayEquals(a, test.arr(b));
    }

    @Test(expected = RuntimeException.class)
    public void notFour(){
        int [] ints = new int[]{1,7,8,9};
        Assert.assertArrayEquals(a, test.arr(b));
    }
    @Test
    public void oneAndFour(){
        Assert.assertTrue(test.arr2(b));
    }
}
