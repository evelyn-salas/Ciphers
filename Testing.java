import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assume.assumeTrue;
import java.util.*;

public class Testing {

  @Test
  @DisplayName("EXAMPLE TEST CASE - VariableConcealment Example")
  public void secondCaseTest() {
    // Skip the test if the Cipher constants don't allow for "HELLO"
    assumeTrue(Cipher.MIN_CHAR <= (int) ('E') && Cipher.MAX_CHAR >= (int) ('O'));

    List<Integer> fillers = List.of(1, 2, 3);
    Cipher testConcealment = new VariableConcealment(fillers);

    String encrypted = testConcealment.encrypt("HELLO");
    // Expecting 1, 2, 3, 1, 2 filler chars + 1 real char for every
    // input character
    int expectedLength = 0;
    for (int i = 0; i < "HELLO".length(); i++) {
      expectedLength += 1 + fillers.get(i % fillers.size());
    }

    // Could append up to 3 filler chars at the very end of
    // encrypted string, check possible length range
    int optionalAdd = fillers.get(("HELLO".length() + 1) % fillers.size());
    assertTrue(encrypted.length() >= expectedLength &&
        encrypted.length() <= expectedLength + optionalAdd);

    // Make sure the string can be decrypted
    assertEquals("HELLO", testConcealment.decrypt(encrypted));

    // Per the spec, we should throw an IllegalArgumentException if
    // the fillers list is empty
    assertThrows(IllegalArgumentException.class, () -> {
      new VariableConcealment(List.of());
    });
  }

  @Test
  @DisplayName("EXAMPLE TEST CASE - ConstantConcealment Example")
  public void firstCaseTest() {
    // Skip the test if the Cipher constants don't allow for "HELLO"
    assumeTrue(Cipher.MIN_CHAR <= (int) ('E') && Cipher.MAX_CHAR >= (int) ('O'));

    Cipher testConcealment = new ConstantConcealment(2);

    String encrypted = testConcealment.encrypt("HELLO");
    // Expecting 2 filler chars + 1 real char for every
    // input character
    int expectedLength = 3 * "HELLO".length();

    // Could append up to 2 filler chars at the very end of
    // encrypted string, check possible length range
    assertTrue(encrypted.length() >= expectedLength &&
        encrypted.length() <= expectedLength + 2);

    // Make sure the string can be decrypted
    assertEquals("HELLO", testConcealment.decrypt(encrypted));

    // Per the spec, we should throw an IllegalArgumentException if
    // the filler is not positive
    assertThrows(IllegalArgumentException.class, () -> {
      new ConstantConcealment(0);
    });
  }
}