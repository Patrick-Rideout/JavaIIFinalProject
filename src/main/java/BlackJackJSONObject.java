import org.json.simple.JSONObject;

/**
 * The BlackJackJSONObject class provides utility methods for creating JSON
 * objects related to the Blackjack game. It includes methods for formatting
 * messages to be sent between the client and server.
 */
public class BlackJackJSONObject {

    /**
     * Creates a JSON object with a single key-value pair where the key is "action"
     * and the value is the provided message.
     *
     * @param msg The message to be included in the JSON object.
     * @return A JSONObject containing the message under the key "action".
     */
    static JSONObject getObject(String msg) {
        JSONObject obj = new JSONObject();
        obj.put("action", msg);
        return obj;
    }
}
