package tester;

import org.json.JSONObject;

public class JSONMessageCreator {

	public static JSONObject generateMesage(String key, String value) {
		JSONObject json = new JSONObject();
		json.append(key, value);
		return json;
	}

}
