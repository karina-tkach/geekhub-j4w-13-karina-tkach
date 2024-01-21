import org.geekhub.hw12.ciphers.model.VigenereCipherDto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VigenereCipherDtoTest {

    @Test
    void testMessageGetterSetter() {
        VigenereCipherDto cipherDto = new VigenereCipherDto();
        cipherDto.setMessage("Hello");
        assertEquals("Hello", cipherDto.getMessage(), "Message getter or setter is not working correctly.");
    }

    @Test
    void testKeywordGetterSetter() {
        VigenereCipherDto cipherDto = new VigenereCipherDto();
        cipherDto.setKeyword("Secret");
        assertEquals("Secret", cipherDto.getKeyword(), "Keyword getter or setter is not working correctly.");
    }

    @Test
    void testEqualsMethod() {
        VigenereCipherDto cipherDto1 = new VigenereCipherDto();
        cipherDto1.setMessage("Hello");
        cipherDto1.setKeyword("Secret");

        VigenereCipherDto cipherDto2 = new VigenereCipherDto();
        cipherDto2.setMessage("Hello");
        cipherDto2.setKeyword("Secret");

        VigenereCipherDto cipherDto3 = new VigenereCipherDto();
        cipherDto3.setMessage("Hi");
        cipherDto3.setKeyword("Secret");

        assertEquals(cipherDto1, cipherDto2, "Equals method returned false for equal objects.");
        assertNotEquals(cipherDto1, cipherDto3, "Equals method returned true for different objects.");
    }

    @Test
    void testHashCodeMethod() {
        VigenereCipherDto cipherDto1 = new VigenereCipherDto();
        cipherDto1.setMessage("Hello");
        cipherDto1.setKeyword("Secret");

        VigenereCipherDto cipherDto2 = new VigenereCipherDto();
        cipherDto2.setMessage("Hello");
        cipherDto2.setKeyword("Secret");

        VigenereCipherDto cipherDto3 = new VigenereCipherDto();
        cipherDto3.setMessage("Hi");
        cipherDto3.setKeyword("Secret");

        assertEquals(cipherDto1.hashCode(), cipherDto2.hashCode(), "HashCode method returned different values for equal objects.");
        assertNotEquals(cipherDto1.hashCode(), cipherDto3.hashCode(), "HashCode method returned the same value for different objects.");
    }

    @Test
    void testToStringMethod() {
        VigenereCipherDto cipherDto = new VigenereCipherDto();
        cipherDto.setMessage("Hello");
        cipherDto.setKeyword("Secret");

        String expectedString = """
                class VigenereCipherDto {
                    message: Hello
                    keyword: Secret
                }""";

        assertEquals(expectedString, cipherDto.toString(), "ToString method returned an unexpected string representation.");
    }
}
