package com.ahmed.data.readers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.ahkamel.data.readers.CustomerLineReader;
import com.ahkamel.models.Customer;

public class CustomerLineReaderTest {
	
	
	@Test
	public void testCreateCustomer() {
		CustomerLineReader lineReader = new CustomerLineReader();
		Customer customer = lineReader
				.processLine("1,Ahmed,Kamel,ahmed.dohair@gmail.com", ",");

		assertEquals("1", customer.getId());
		assertEquals("Ahmed", customer.getFirstName());
		assertEquals("Kamel", customer.getLastName());
		assertEquals("ahmed.dohair@gmail.com", customer.getEmail());
	}


}
