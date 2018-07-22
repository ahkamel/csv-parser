package com.ahkamel.data.parsers;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ahkamel.data.readers.CustomerLineReader;
import com.ahkamel.exceptions.ReadFileException;
import com.ahkamel.models.Customer;
import com.ahkamel.util.Util;

/**
 * Parsing the CSV file into small batches ؛
 * 
 * @author Ahmed Kamel
 *
 */
public class CsvParser {
	private static final Logger logger = LoggerFactory.getLogger(CsvParser.class);

	private static String DEFAULT_SEPERATOR = ",";
	private int batchSize = 100;
	private Path path;
	private String charSet;
	private String separator;
	private String header;
	private CustomerLineReader customerLineReader;

	public CsvParser(String filePath, String header, int batchSize) {
		this(filePath, "UTF-8", DEFAULT_SEPERATOR, header, batchSize);
	}

	public CsvParser(String filePath, String charSet, String separator, String header, int batchSize) {
		this.path = Paths.get(filePath);
		this.charSet = charSet;
		this.separator = separator;
		this.header = header;
		// As if this just two cases no need to hide them in a factory design pattern
		this.customerLineReader = new CustomerLineReader();
		this.setBatchSize(batchSize);
	}

	public void forEachBatch(Consumer<List<Customer>> action) throws ReadFileException {
		if (logger.isDebugEnabled()) {
			logger.debug("Start prasing csv file batch size: {}", this.batchSize);
		}
		buildBatch((customerList) -> acceptBatch(action, customerList));
	}

	private void acceptBatch(Consumer<List<Customer>> action, List<Customer> customerList) {
		List<Customer> newCustomerList = Util.createBatchList(customerList);
		action.accept(newCustomerList);
		customerList.clear();
	}

	private boolean isBatchReady(Scanner scanner, int index) {
		return this.batchSize == 1 || (index % this.batchSize) == 0 || scanner.hasNextLine() == false;
	}

	public List<CompletableFuture<Void>> forEachBatchAsync(
			Function<List<Customer>, CompletableFuture<Void>> action) throws ReadFileException {
		if (logger.isDebugEnabled()) {
			logger.debug("Start prasing csv file asynchronusly batch size: {}", this.batchSize);
		}
		List<CompletableFuture<Void>> completableFutureList = new ArrayList<>();
		buildBatch((customerList) -> applyBatch(action, completableFutureList, customerList));
		return completableFutureList;
	}
	
	
	private void buildBatch(Consumer<List<Customer>> consumer) throws ReadFileException {
		try (Scanner scanner = new Scanner(path, charSet)) {
			List<Customer> customerList = new ArrayList<>();
			int index = 1;
			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				if (validData(line)) {
					Customer customer = customerLineReader.processLine(line, separator);
					customerList.add(customer);
					if (isBatchReady(scanner, index)) {
						consumer.accept(customerList);
					}
					index++;
				}

			}
		} catch (IOException e) {
			throw new ReadFileException("I/O Error while reading the CSV file", e);
		}

	}

	private void applyBatch(Function<List<Customer>, CompletableFuture<Void>> action,
			List<CompletableFuture<Void>> completableFutureList, List<Customer> customerList) {
		List<Customer> newCustomerList = Util.createBatchList(customerList);
		completableFutureList.add(action.apply(newCustomerList));
		customerList.clear();
	}

	private boolean validData(String line) {
		return !"".equals(line) && !line.equals(header);
	}

	public int getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

}
