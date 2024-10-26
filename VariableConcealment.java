import java.util.*;
// This class represents a concealment encryption/decryption scheme. This Cipher
// works by "hiding" the original message at variable locations in the encrypted message.
// This class extends Cipher

public class VariableConcealment extends Cipher {
  public static final Random RAND = new Random();

  private List<Integer> fillers;

  /**
   * Constructs a new VariableConcealment. Requires that setFillers is called
   * before messages can be encrypted / decrypted
   * Parameters:
   * - none
   * Returns:
   * - none
   */
  public VariableConcealment() {
    this.fillers = null;
  }

  /**
   * Constructs a new VariableConcealment.
   * Parameters:
   * - List<Integer> fillers: the filler values (number of "junk" characters) to
   * use when encrypting / decrypting
   * Returns:
   * - none
   * Exceptions:
   * - Throws an IllegalArgumentException if the given list is empty OR
   * it contains non-positive values
   */
  public VariableConcealment(List<Integer> fillers) {
    setFillers(fillers);
  }

  /**
   * Checks that the provided list of filler values is valid (non-empty containing
   * only
   * positive values)
   * Parameters:
   * - List<Integer> fillers: the filler values to check
   * Returns:
   * - none
   * Exceptions:
   * - Throws an IllegalArgumentException if the given list is empty OR
   * it contains non-positive values
   */
  private void checkFillers(List<Integer> fillers) {
    if (fillers.size() == 0) {
      throw new IllegalArgumentException("Provided list of fillers can't be empty");
    }
    for (int x : fillers) {
      if (x <= 0) {
        throw new IllegalArgumentException("Provided list of fillers can't have " +
            "non-positive values");
      }
    }
  }

  /**
   * Updates the list of filler values (number of "junk" characters) to use on
   * encryption / decryption
   * Parameters:
   * - List<Integer> fillers: the filler values to update to
   * Returns:
   * - none
   * Exceptions:
   * - Throws an IllegalArgumentException if the given list is empty OR
   * it contains non-positive values
   */
  public void setFillers(List<Integer> fillers) {
    checkFillers(fillers);
    this.fillers = new ArrayList<>(fillers);
  }

  /**
   * Encrypts a given string by inserting "junk" characters as determined by the
   * previously set fillers
   * Parameters:
   * - String input: the string to encrypt
   * Returns:
   * - String: an encrypted String
   * Exceptions:
   * - Throws an IllegalStateException if the fillers were never previously set
   */
  @Override
  public String encrypt(String input) {
    if (this.fillers == null) {
      // Fillers will only be null if empty constructor called without being set later
      throw new IllegalStateException("Fillers never set after empty construction");
    }

    String ret = "";
    int fillerIndex = 0;
    for (int i = 0; i < input.length(); i++) {
      // Determine the next filler value (looping around if necessary via %)
      int filler = fillers.get(fillerIndex % fillers.size());
      for (int j = 0; j < filler; j++) {
        // Let's generate *filler* number of random characters and append
        // it to our output String
        ret += (char) (Cipher.MIN_CHAR + RAND.nextInt(Cipher.TOTAL_CHARS));
      }
      // For every *filler* number of random characters characters we generate,
      // we want to add a character from our input message
      ret += input.charAt(i);
      // Increment to the next filler value
      fillerIndex++;
    }
    return ret;
  }

  /**
   * Decrypts a given string by removing "junk" characters as determined by the
   * previously set fillers
   * Parameters:
   * - String input: the string to decrypt
   * Returns:
   * - String: a decrypted String
   * Exceptions:
   * - Throws an IllegalStateException if the fillers were never previously set
   */
  @Override
  public String decrypt(String input) {
    if (this.fillers == null) {
      // Fillers will only be null if empty constructor called without being set later
      throw new IllegalStateException("Fillers never set after empty construction");
    }

    String ret = "";
    int fillerIndex = 0;
    int filler = fillers.get(fillerIndex);
    for (int i = filler; i < input.length(); i += filler + 1) {
      // We know that our "real" message happens every (filler + 1)
      // characters. Therefore, let's iterate through our input
      // and find the character that happens every (filler + 1)
      ret += input.charAt(i);
      // Determine the next filler value, looping around to the front if necessary
      fillerIndex++;
      filler = fillers.get(fillerIndex % fillers.size());
    }
    return ret;
  }
}