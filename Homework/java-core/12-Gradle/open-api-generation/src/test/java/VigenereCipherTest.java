import org.geekhub.hw12.ciphers.VigenereCipher;
import org.geekhub.hw12.ciphers.model.VigenereCipherDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VigenereCipherTest {

    private VigenereCipher cipher;

    @BeforeEach
    public void setUp() {
        cipher = new VigenereCipher();
    }

    @Test
    void testEncode() {
        VigenereCipherDto encodeData = new VigenereCipherDto();
        encodeData.setMessage("Hello");
        encodeData.setKeyword("KEY");
        assertEquals("Rijvs", cipher.encode(encodeData), "The encode method did not provide the correct encryption.");
    }

    @Test
    void testDecode() {
        VigenereCipherDto decodeData = new VigenereCipherDto();
        decodeData.setMessage("RIJVS");
        decodeData.setKeyword("KEY");
        assertEquals("HELLO", cipher.decode(decodeData), "The decode method did not provide the correct decryption.");
    }

    @Test
    void testEncodeDecodeInverse() {
        VigenereCipherDto encodeData = new VigenereCipherDto();
        encodeData.setMessage("HELLO");
        encodeData.setKeyword("KEY");
        String encodedMessage = cipher.encode(encodeData);

        VigenereCipherDto decodeData = new VigenereCipherDto();
        decodeData.setMessage(encodedMessage);
        decodeData.setKeyword("KEY");
        String decodedMessage = cipher.decode(decodeData);

        assertEquals("HELLO", decodedMessage, "Encoding and then decoding did not result in the original message.");
    }

    @Test
    void testNonAlphabeticCharacters() {
        VigenereCipherDto encodeData = new VigenereCipherDto();
        encodeData.setMessage("Hello, World!");
        encodeData.setKeyword("KEY");
        assertEquals("Rijvs, Uyvjn!", cipher.encode(encodeData), "Non-alphabetic characters were not preserved during encoding.");

        VigenereCipherDto decodeData = new VigenereCipherDto();
        decodeData.setMessage("Rijvs, Uyvjn!");
        decodeData.setKeyword("KEY");
        assertEquals("Hello, World!", cipher.decode(decodeData), "Non-alphabetic characters were not preserved during decoding.");
    }
}
