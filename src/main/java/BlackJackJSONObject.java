import org.json.simple.JSONObject;

public class BlackJackJSONObject {

    static JSONObject getObject(String msg) {
        JSONObject obj = new JSONObject();
        obj.put("string", msg);
        return obj;
    }
}
