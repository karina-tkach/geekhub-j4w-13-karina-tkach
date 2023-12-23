package org.geekhub.hw10;

public class CatTest {
    private Cat firstCat;
    private Cat secondCat;

    @BeforeMethod
    public void setUp() {
        firstCat = new Cat();
        secondCat = new Cat(4, 2, "Beige");
    }

    @Test
    public void testEqualityOfPaws() {
        boolean result = firstCat.getPawsNumber() == secondCat.getPawsNumber();
        CustomAssertions.assertEquals(true, result);
    }

    @Test
    public void testEqualityOfEnergy() {
        int firstEnergy = firstCat.getEnergy();
        secondCat.sleep();
        int secondEnergy = secondCat.getEnergy();
        CustomAssertions.assertEquals(firstEnergy, secondEnergy);
    }

    @Test
    public void testException() {
        CustomAssertions.assertThrows(() -> new Cat(4, -1, "W"));
    }

    @Test
    public void testEqualityOfObjects() throws IllegalAccessException {
        CustomAssertions.assertReflectionEquals(firstCat, new Cat(firstCat));
    }
    @Test
    public void testEqualityOfDifferentObjects() throws IllegalAccessException {
        CustomAssertions.assertReflectionEquals(firstCat, secondCat);
    }
}
