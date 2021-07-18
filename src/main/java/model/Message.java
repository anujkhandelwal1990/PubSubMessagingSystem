package model;

import org.json.JSONObject;

/**
 * 
 * @author anujkhandelwal
 *
 */
public class Message {

	private final JSONObject msg;

	public Message(JSONObject msg) {
		this.msg = msg;
	}
	public JSONObject getMsg() {
		return msg;
	}
}