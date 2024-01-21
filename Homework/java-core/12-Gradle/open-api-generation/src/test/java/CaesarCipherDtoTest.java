import org.geekhub.hw12.ciphers.model.CaesarCipherDto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CaesarCipherDtoTest {

    @Test
    void testMessageGetterSetter() {
        CaesarCipherDto dto = new CaesarCipherDto();
        dto.setMessage("Hello");
        assertEquals("Hello", dto.getMessage(), "Getter or setter for message was incorrect.");
    }

    @Test
    void testShiftGetterSetter() {
        CaesarCipherDto dto = new CaesarCipherDto();
        dto.setShift(13);
        assertEquals(13, dto.getShift(), "Getter or setter for shift was incorrect.");
    }

    @Test
    void testEqualsMethod() {
        CaesarCipherDto dto1 = new CaesarCipherDto().message("Hello").shift(3);
        CaesarCipherDto dto2 = new CaesarCipherDto().message("Hello").shift(3);
        CaesarCipherDto dto3 = new CaesarCipherDto().message("World").shift(5);

        assertEquals(dto1, dto2, "Equals method returned false for equal objects.");
        assertNotEquals(dto1, dto3, "Equals method returned true for non-equal objects.");
    }

    @Test
    void testHashCodeMethod() {
        CaesarCipherDto dto1 = new CaesarCipherDto().message("Hello").shift(3);
        CaesarCipherDto dto2 = new CaesarCipherDto().message("Hello").shift(3);
        CaesarCipherDto dto3 = new CaesarCipherDto().message("World").shift(5);

        assertEquals(dto1.hashCode(), dto2.hashCode(), "HashCode method returned different values for equal objects.");
        assertNotEquals(dto1.hashCode(), dto3.hashCode(), "HashCode method returned the same value for non-equal objects.");
    }

    @Test
    void testToStringMethod() {
        CaesarCipherDto dto = new CaesarCipherDto().message("Hello").shift(3);
        String expectedString = """
                class CaesarCipherDto {
                    message: Hello
                    shift: 3
                }""";
        assertEquals(expectedString, dto.toString(), "ToString method returned incorrect string representation.");
    }
}
