import java.util.*;
// This class represents a concealment encryption/decryption scheme. This Cipher
// works by "hiding" the original message at consistent locations in the encrypted message.
// This class extends VariableConcealment

public class ConstantConcealment extends VariableConcealment {

  /**
   * Constructs a new ConstantConcealment
   * Parameters:
   * - int filler: the number of filler characters to place
   * Returns:
   * - none
   * Exceptions:
   * - Throws an IllegalArgumentException if the given
   * filler is not positive
   */
  public ConstantConcealment(int filler) {
    super();
    if (filler <= 0) {
      throw new IllegalArgumentException("Provided filler value must be positive");
    }

    // All we have to do is use super class with a singular list!
    List<Integer> fillers = new ArrayList<>();
    fillers.add(filler);

    // Set the fillers, no need to implement encrypt / decrypt since
    // the superclass will do it for us!
    super.setFillers(fillers);
  }

  /**
   * Updates the list of filler values (number of "junk" characters) to use on
   * encryption / decryption
   * Parameters:
   * - List<Integer> fillers: the filler values to update to
   * Returns:
   * - none
   * Exceptions:
   * - Throws an UnsuportedOperationException if called
   */
  public void setFillers(List<Integer> fillers) {
    // Not required by the spec, but probably a good idea to prevent a client from
    // setting
    // the fillers of a ConstantConcealment
    throw new UnsupportedOperationException("Unable to set fillers for ConstantConcealment");
  }
}