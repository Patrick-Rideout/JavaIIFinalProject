import java.io.PrintStream;

/**
 * The FinalProject.PlayingCard class represents a playing card with a value and a suit.
 */
public class PlayingCard {
    public static final int ACE = 14;
    private int value;
    private Suit suit;

    /**
     * Constructs a FinalProject.PlayingCard with the specified value and suit.
     *
     * @param value The numerical value of the card.
     * @param suit  The suit of the card.
     * @throws IllegalArgumentException if the value is invalid.
     */
    public PlayingCard(int value, Suit suit) {
        if (value < 2 || value > ACE) {
            throw new IllegalArgumentException("Invalid card value");
        }
        this.value = value;
        this.suit = suit;
    }

    /**
     * Retrieves the numerical value of the card.
     *
     * @return The numerical value of the card.
     */
    public int getValue() {
        return value;
    }

    /**
     * Retrieves the suit of the card.
     *
     * @return The suit of the card.
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * Overrides the toString method to represent the card as a String.
     *
     * @return The String representation of the card.
     * @throws IllegalArgumentException if the suit value is invalid.
     */

    @Override
    public String toString() {
        String valueStr;
        if (value == 11) {
            valueStr = "J";
        } else if (value == 12) {
            valueStr = "Q";
        } else if (value == 13) {
            valueStr = "K";
        } else if (value == 14) {
            valueStr = "A";
        } else {
            valueStr = String.valueOf(value);
        }

        String suitSymbol;
        if (suit == Suit.DIAMONDS) {
            suitSymbol = "\u2666";
        } else if (suit == Suit.HEARTS) {
            suitSymbol = "\u2665";
        } else if (suit == Suit.CLUBS) {
            suitSymbol = "\u2663";
        } else if (suit == Suit.SPADES) {
            suitSymbol = "\u2660";
        } else {
            throw new IllegalArgumentException("Invalid suit value");
        }

        return valueStr + " " + suitSymbol;
    }

    /**
     * Prints an ASCII representation of the playing card.
     *
     * @param playingCard The playing card to print.
     * @param printStream The PrintStream to print the card representation.
     */
    public static void printAsciiCard(PlayingCard playingCard, PrintStream printStream) {
        String suitSymbol = switch (playingCard.getSuit()) {
            case DIAMONDS -> "\u2666";
            case HEARTS -> "\u2665";
            case CLUBS -> "\u2663";
            case SPADES -> "\u2660";
        };

        int intCardValue = playingCard.getValue();

        String strCardValue;
        if (intCardValue == 11) {
            strCardValue = "J";
        } else if (intCardValue == 12) {
            strCardValue = "Q";
        } else if (intCardValue == 13) {
            strCardValue = "K";
        } else if (intCardValue == 14) {
            strCardValue = "A";
        } else {
            strCardValue = String.valueOf(intCardValue);
        }

         printStream.println(" _______");
         printStream.printf("|%s      |\n", strCardValue);
         printStream.printf("|   %s   |\n", suitSymbol);
         printStream.printf("|      %s|\n", strCardValue);
         printStream.println(" -------");
    }

    /**
     * Enum representing the four suits of a playing card.
     */
    public enum Suit {DIAMONDS, HEARTS, CLUBS, SPADES}
}

