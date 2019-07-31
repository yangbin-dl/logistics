package com.mallfe.common.json;

public class JsonData extends JsonObject {

    private int total;

    private Object data;
    
    private Object page;

    public JsonData() {
        this.success = true;
    }

    public JsonData(Object data) {
        this.data = data;
        this.success = true;
    }
    
    public JsonData(Object data,Object page) {
        this.data = data;
        this.success = true;
        this.page = page;
    }

    public JsonData(String message) {
        this.message = message;
        this.success = true;
    }

    public JsonData(Object data, int total) {
        this.data = data;
        this.total = total;
        this.success = true;
    }

    public JsonData(Object data, String message) {
        this.data = data;
        this.message = message;
        this.success = true;
    }

    public JsonData(String code, String message) {
        this.code = code;
        this.message = message;
        this.success = true;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

	public Object getPage() {
		return page;
	}

	public void setPage(Object page) {
		this.page = page;
	}

}