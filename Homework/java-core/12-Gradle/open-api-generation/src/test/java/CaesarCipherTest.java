import org.geekhub.hw12.ciphers.CaesarCipher;
import org.geekhub.hw12.ciphers.model.CaesarCipherDto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CaesarCipherTest {

    @Test
    void testEncode() {
        CaesarCipher cipher = new CaesarCipher();
        CaesarCipherDto encodeData = new CaesarCipherDto();
        encodeData.setMessage("Hello, World!");
        encodeData.setShift(3);
        assertEquals("Khoor, Zruog!", cipher.encode(encodeData), "The encoding was incorrect.");
    }

    @Test
    void testDecode() {
        CaesarCipher cipher = new CaesarCipher();
        CaesarCipherDto decodeData = new CaesarCipherDto();
        decodeData.setMessage("Khoor, Zruog!");
        decodeData.setShift(3);
        assertEquals("Hello, World!", cipher.decode(decodeData), "The decoding was incorrect.");
    }

    @Test
    void testEncodeDecodeInverse() {
        CaesarCipher cipher = new CaesarCipher();
        CaesarCipherDto encodeData = new CaesarCipherDto();
        encodeData.setMessage("Hello, World!");
        encodeData.setShift(5);
        String encodedMessage = cipher.encode(encodeData);
        CaesarCipherDto decodeData = new CaesarCipherDto();
        decodeData.setMessage(encodedMessage);
        decodeData.setShift(5);
        String decodedMessage = cipher.decode(decodeData);
        assertEquals("Hello, World!", decodedMessage, "Encoding and decoding are not inverse operations.");
    }

    @Test
    void testEncodeNonAlphabeticCharacters() {
        CaesarCipher cipher = new CaesarCipher();
        CaesarCipherDto encodeData = new CaesarCipherDto();
        encodeData.setMessage("123!@#$%^&*()");
        encodeData.setShift(7);
        assertEquals("123!@#$%^&*()", cipher.encode(encodeData), "Non-alphabetic characters were affected by encoding.");
    }

    @Test
    void testDecodeNonAlphabeticCharacters() {
        CaesarCipher cipher = new CaesarCipher();
        CaesarCipherDto decodeData = new CaesarCipherDto();
        decodeData.setMessage("123!@#$%^&*()");
        decodeData.setShift(7);
        assertEquals("123!@#$%^&*()", cipher.decode(decodeData), "Non-alphabetic characters were affected by decoding.");
    }

    @Test
    void testEmptyMessage() {
        CaesarCipher cipher = new CaesarCipher();
        CaesarCipherDto encodeData = new CaesarCipherDto();
        encodeData.setMessage("");
        encodeData.setShift(10);
        String encodedMessage = cipher.encode(encodeData);
        CaesarCipherDto decodeData = new CaesarCipherDto();
        decodeData.setMessage(encodedMessage);
        decodeData.setShift(10);
        String decodedMessage = cipher.decode(decodeData);
        assertEquals("", decodedMessage, "Encoding and decoding an empty message should result in an empty message.");
    }
}
