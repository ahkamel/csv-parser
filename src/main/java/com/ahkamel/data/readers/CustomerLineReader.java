package com.ahkamel.data.readers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ahkamel.models.Customer;

/**
 * 
 * Reads CSV line and converts it to a Customer POJO object
 * @author Ahmed Kamel
 *
 */
public class CustomerLineReader  {
	private static final Logger logger = LoggerFactory.getLogger(CustomerLineReader.class);

	
	public Customer processLine(String line, String separator) {
		String[] lineTokens = line.split(separator);
		return createCusotmer(lineTokens);
	}

	private Customer createCusotmer(String[] lineTokens) {
	/*	if(logger.isDebugEnabled()) {
			logger.debug("Create Customer object from line tokens: {}", (Object)lineTokens);
		}*/
		Customer customer = new Customer();
		customer.setId(lineTokens[0]);
		customer.setFirstName(lineTokens[1]);
		customer.setLastName(lineTokens[2]);
		customer.setEmail(lineTokens[3]);
/*		if(logger.isDebugEnabled()) {
			logger.debug("Customer object: {}", customer);
		}
*/		return customer;
	}

}
