package com.ahmed.data.parsers;

import static org.junit.Assert.assertEquals;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ahkamel.data.parsers.BatchConsumer;
import com.ahkamel.data.parsers.CsvParser;
import com.ahkamel.data.readers.CustomerLineReader;
import com.ahkamel.exceptions.ReadFileException;
import com.ahkamel.exceptions.ServerConnectException;
import com.ahkamel.models.Customer;

public class BatchConsumerTest {

	private static final int BATCH_SIZE = 5;
	private static File generatedFile;
	private static final String HEADER = "customerId,firstName,lastName,email";
	private static List<Customer> fullCustomerList = new ArrayList<>();
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		generatedFile = File.createTempFile("batch", "csv");
		fillCsvFile(generatedFile);
	}

	private static void fillCsvFile(File file) throws IOException {
		BufferedWriter output = null;
		try {
			output = new BufferedWriter(new FileWriter(file));
			output.write(HEADER);
			output.newLine();
			for (int i = 1; i <= 500; i++) {
				output.write(getLine(i));
				output.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (output != null)
				output.close();
		}
	}

	private static String getLine(int i) {
		String firstName = RandomStringUtils.randomAlphabetic(8);
		String lastName = RandomStringUtils.randomAlphabetic(8);
		String email = firstName + "." + lastName + "@gmail.com";
		
		String line = i + "," + firstName + "," + lastName + "," + email;
		fullCustomerList.add(new CustomerLineReader().processLine(line, ","));
		return line;
	}

	@Test
	public void testReceiveBatch() {
		BatchConsumer fileUpload = new BatchConsumer();

		CsvParser csvParser = new CsvParser(generatedFile.getAbsolutePath(), HEADER, BATCH_SIZE);
		try {
			fileUpload.receiveBatch(csvParser, (customerList)-> assertEquals(BATCH_SIZE, customerList.size()));
			fileUpload.receiveBatch(csvParser, (customerList)-> assertEquals(true, fullCustomerList.containsAll(customerList)));
		} catch (ServerConnectException | ReadFileException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testUploadDataAsync() throws Exception {
		BatchConsumer fileUpload = new BatchConsumer();

		CsvParser csvParser = new CsvParser(generatedFile.getAbsolutePath(), HEADER, BATCH_SIZE);
		try {
			fileUpload.receiveBatchAsync(csvParser, (customerList)-> {try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(customerList);
			assertEquals(BATCH_SIZE, customerList.size());});
			fileUpload.receiveBatchAsync(csvParser, (customerList)-> assertEquals(true, fullCustomerList.containsAll(customerList)));
		} catch (ServerConnectException | ReadFileException e) {
			e.printStackTrace();
		}
	}
}
