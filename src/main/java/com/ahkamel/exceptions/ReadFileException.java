package com.ahkamel.exceptions;

/**
 * 
 * Thrown in case of issues while trying to read the file
 * 
 * This is just a dummy exception where this exception should have 
 * more details about the context where the exception has been thrown
 * 
 * 
 * @author Ahmed Kamel
 *
 */
public class ReadFileException extends Exception{

	public ReadFileException(String message, Throwable cause) {
		super(message, cause);
	}


}
