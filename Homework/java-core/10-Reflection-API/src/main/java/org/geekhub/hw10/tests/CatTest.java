package org.geekhub.hw10.tests;

import org.geekhub.hw10.framework.BeforeMethod;
import org.geekhub.hw10.classes.Cat;
import org.geekhub.hw10.framework.CustomAssertions;
import org.geekhub.hw10.framework.Test;

@SuppressWarnings("all")
public class CatTest {
    private Cat firstCat;
    private Cat secondCat;

    @BeforeMethod
    public void setUp() {
        firstCat = new Cat();
        secondCat = new Cat(4, 2, "Beige");
    }

    @Test
    public void getPawsNumber_whenSameToSecond_shouldBeEqual() {
        boolean result = firstCat.getPawsNumber() == secondCat.getPawsNumber();
        CustomAssertions.assertEquals(true, result);
    }

    @Test
    public void getEnergy_whenDiffer_shouldThrowAssertionError() {
        int firstEnergy = firstCat.getEnergy();
        secondCat.sleep();
        int secondEnergy = secondCat.getEnergy();
        CustomAssertions.assertEquals(firstEnergy, secondEnergy);
    }

    @Test
    public void constructor_whenInvalidArguments_shouldThrowException() {
        CustomAssertions.assertThrows(() -> new Cat(4, -1, "W"), IllegalArgumentException.class);
    }

    @Test
    public void equal_whenIdenticalObjects_shouldBeEqual() throws IllegalAccessException {
        CustomAssertions.assertReflectionEquals(firstCat, new Cat(firstCat));
    }

    @Test
    public void equal_whenDifferentObjects_shouldThrowAssertionError() throws IllegalAccessException {
        CustomAssertions.assertReflectionEquals(firstCat, secondCat);
    }
}
