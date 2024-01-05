package org.geekhub.hw10.tests;

import org.geekhub.hw10.framework.AfterMethod;
import org.geekhub.hw10.framework.BeforeMethod;
import org.geekhub.hw10.framework.CustomAssertions;
import org.geekhub.hw10.framework.Test;

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

    private Integer[] getTwoNumbers(){
        return new Integer[]{2,3};
    }
    @Test(parameterSource = "getTwoNumbers")
    public void min_whenExpectedResult_shouldBeEqual(Object[] numbers) {
        int firstNumber = (int)numbers[0];
        int secondNumber = (int)numbers[1];
        int result = Math.min(firstNumber, secondNumber);
        CustomAssertions.assertEquals(firstNumber, result);
    }
    @Test
    public void min_whenDifferFromExpectedResult_shouldThrowAssertionError() {
        int result = Math.min(0, 5);
        CustomAssertions.assertEquals(-1, result);
    }

    @Test
    public void min_whenProduceExpectedResult_shouldBeEqual() {
        int result = Math.min(10, 7);
        CustomAssertions.assertEquals(7, result);
    }

    @Test
    public void max_whenDifferFromExpectedResult_shouldThrowAssertionError() {
        int result = Math.max(10, 5);
        CustomAssertions.assertEquals(11, result);
    }
}
