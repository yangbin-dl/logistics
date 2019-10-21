package com.mallfe.item.pojo;

import lombok.Data;

@Data
public class UploadResponseMessage {

	private String code = "";
	private String name = "";

	private String returnCode;
	private String returnMessage;

}
