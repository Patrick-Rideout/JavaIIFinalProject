import org.json.simple.JSONObject;

/**
 * Utility class for creating JSON objects with a given message.
 * This class uses the json-simple library to construct JSON objects.
 */
public class BlackJackJSONObject {

    /**
     * Creates a JSON object with a specified message.
     *
     * @param msg the message to include in the JSON object
     * @return a JSON object containing the provided message
     */
    static JSONObject getObject(String msg) {
        JSONObject obj = new JSONObject();
        obj.put("action", msg);
        return obj;
    }
}
