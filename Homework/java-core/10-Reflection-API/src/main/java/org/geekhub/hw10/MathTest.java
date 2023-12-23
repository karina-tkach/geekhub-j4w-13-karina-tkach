package org.geekhub.hw10;

@SuppressWarnings("all")

public class MathTest {
    @BeforeMethod
    public void setUp() {
        System.out.println("Setting up MathTest");
    }

    @AfterMethod
    public void tearDown() {
        System.out.println("Tearing down MathTest");
    }

    @Test
    public void testMin() {
        int result = Math.min(2, 3);
        CustomAssertions.assertEquals(2, result);
    }
    @Test
    public void testMin_Error() {
        int result = Math.min(0, 5);
        CustomAssertions.assertEquals(-1, result);
    }

    @Test
    public void testMin_Second() {
        int result = Math.min(10, 7);
        CustomAssertions.assertEquals(7, result);
    }

    @Test
    public void testMax() {
        int result = Math.max(10, 5);
        CustomAssertions.assertEquals(11, result);
    }
}
