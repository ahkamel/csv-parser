package com.ahmed.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.ahkamel.models.Customer;
import com.ahkamel.util.Util;

public class UtilTest {

		
	@Test
	public void testCreateBatchList() {
		List<Customer> customerList = new ArrayList<>();
		Customer c1 = new Customer();
		c1.setFirstName("Ahmed");
		Customer c2 = new Customer();
		c2.setFirstName("Ali");
		customerList.add(c1);
		customerList.add(c2);
		
		
		List<Customer> newCustomerList = Util.createBatchList(customerList);
		assertEquals(2, newCustomerList.size());
		assertEquals(false,newCustomerList== customerList);
		assertEquals(customerList.get(0), newCustomerList.get(0));
		assertEquals(customerList.get(1), newCustomerList.get(1));
	}

}
