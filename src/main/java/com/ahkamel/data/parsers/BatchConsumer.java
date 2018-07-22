package com.ahkamel.data.parsers;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.ahkamel.dtos.FileMetadata;
import com.ahkamel.exceptions.ReadFileException;
import com.ahkamel.exceptions.ServerConnectException;
import com.ahkamel.models.Customer;

/**
 * This is to receive the batches generated from the parser
 * 
 * @author Ahmed Kamel
 *
 */
@Component
public class BatchConsumer {

	/**
	 * This calls csv parser forEachBatch method and pass the lambda expression to receive the generated batch
	 * 
	 * @param parser
	 * @throws ServerConnectException
	 * @throws ReadFileException
	 */
	public void receiveBatch(CsvParser parser, Consumer<List<Customer>> action)
			throws ServerConnectException, ReadFileException {
			parser.forEachBatch(action);
		
	}

	/**
	 * 
	 * This calls csv parser forEachBatchAsyn method and pass the BiFunction lambda expression to receive the data asynchronously 
	 * 
	 * @param parser
	 * @param fileMetadata
	 * @throws Exception
	 */
	public void receiveBatchAsync(CsvParser parser, Consumer<List<Customer>> action) throws Exception {
		
		int availableProcessors = Runtime.getRuntime().availableProcessors();
		ExecutorService executor = Executors.newFixedThreadPool(availableProcessors - 1);
			Function<List<Customer>, CompletableFuture<Void>> asyncAction = submitAsyncDataAction(executor, action);
			List<CompletableFuture<Void>> completableFutures = parser.forEachBatchAsync(asyncAction);
			CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[completableFutures.size()])).get();
			executor.shutdown();
		
	}

	

	private Function<List<Customer>, CompletableFuture<Void>> submitAsyncDataAction(ExecutorService executor, Consumer<List<Customer>> action) {
		return (customerList) -> {
			return CompletableFuture.runAsync(() -> {
				action.accept(customerList);
			}, executor);

		};
	}


	

	





}
