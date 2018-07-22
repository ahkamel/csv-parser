package com.ahkamel.exceptions;

import java.io.IOException;

/**
 * A wrapper class for I/O issues to connect with the remote server to
 * post the data
 * 
 * This is just a dummy exception where this exception should have 
 * more details about the context where the exception has been thrown
 * 
 * @author Ahmed Kamel
 *
 */
public class ServerConnectException extends IOException{

	
	public ServerConnectException(String message, Throwable cause) {
		super(message, cause);
	}

	
}
