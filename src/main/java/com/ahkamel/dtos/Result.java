package com.ahkamel.dtos;

/**
 * 
 * The DTO object for response on /parser/parse POST request
 * @author Ahmed Kamel
 *
 */
public class Result {

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
