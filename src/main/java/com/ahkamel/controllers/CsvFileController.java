package com.ahkamel.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ahkamel.data.parsers.CsvParser;
import com.ahkamel.data.parsers.BatchConsumer;
import com.ahkamel.dtos.FileMetadata;
import com.ahkamel.dtos.Result;
import com.ahkamel.starter.Application;
import com.ahkamel.validators.BaseValidator;

/**
 * 
 * Rest service to parse the file
 * 
 * It has only one service POST /parser/parse
 * @author Ahmed Kamel
 *
 */
@RestController
public class CsvFileController {
	private static final Logger logger = LoggerFactory.getLogger(Application.class);
	private static final String PARALLEL = "parallel";
	private static final String SEQUENTIAL = "sequential";
	private static final String HEADER = "customerId,firstName,LastName,email";
	@Value("${batch.size:100}")
	private  int batchSize;
	@Autowired
	private BatchConsumer batchConsumber;
	@Autowired
	private BaseValidator baseValidator;

	@RequestMapping(value = "/parse", method = RequestMethod.POST)
	public ResponseEntity<Result> parseCsvFile(@RequestBody @Valid FileMetadata fileMetadata, Errors errors) {
		long start = System.currentTimeMillis();
		Result result = new Result();
		if (errors.hasErrors()) {
			String errorMessage = baseValidator.errorToString(errors);
			return getErrorResponse(result, errorMessage);
		}
		CsvParser parser = new CsvParser(fileMetadata.getPath(), HEADER, batchSize);
		if (SEQUENTIAL.equals(fileMetadata.getRunMode())) {
			try {
				
				//TODO:Here you receive the batch of data
				batchConsumber.receiveBatch(parser, customerList -> System.out.println(customerList));
			} catch (Exception e) {
				if (logger.isErrorEnabled()) {
					logger.error("Error while upload the data sequentail {}" + e.getMessage(), e);
				}
				return getErrorResponse(result,"Error while upload the data sequentail: "+ e.getMessage());
			}
		} else if (PARALLEL.equals(fileMetadata.getRunMode())) {
			try {
				//TODO:Here you receive the batch of data
				batchConsumber.receiveBatchAsync(parser, customerList -> System.out.println(customerList));
			} catch (Exception e) {
				if (logger.isErrorEnabled()) {
					logger.error("Error while upload data async {}" + e.getMessage(), e);
				}
				return getErrorResponse(result, "Error while upload data async: " + e.getMessage());
			}
		}
		long end = System.currentTimeMillis();
		result.setMessage("Done! total time (Sec): "+(end -start)/1000);
		return new ResponseEntity<Result>(result, HttpStatus.OK);
	}

		private ResponseEntity<Result> getErrorResponse(Result result, String errorMessage) {
		result.setMessage(errorMessage);
		return ResponseEntity.badRequest().body(result);
	}


}
