package com.ahkamel.dtos;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 
 * Bind the user submitted request /parser/parse POST request
 * @author Ahmed Kamel
 *
 */
public class FileMetadata {
   @NotBlank(message = "File path is required")
	private String path;
	@NotBlank(message = "File mode is reqired values[sequential|parallel]")
	@Pattern(regexp="^(sequential|parallel)$",message="Invalid rum mode option [sequential|parallel]")
	private String runMode;
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getRunMode() {
		return runMode;
	}
	public void setRunMode(String runMode) {
		this.runMode = runMode;
	}

}
