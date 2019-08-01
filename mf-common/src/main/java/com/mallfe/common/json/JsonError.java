package com.mallfe.common.json;

public class JsonError extends JsonObject {

	private Object data;

    public JsonError() {

    }

    public JsonError(String message) {
        this.message = message;
        this.success = false;
    }

    public JsonError(Object data, String message) {
    	this.data = data;
        this.message = message;
        this.success = false;
    }
    
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
    
}