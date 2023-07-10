package com.marsh_mclinnan.weather.controller.advice;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.marsh_mclinnan.weather.controller.response.ExceptionResponse;
import com.marsh_mclinnan.weather.exception.CityNotFoundException;
import com.marsh_mclinnan.weather.exception.ExternalServiceNotWorking;

@RestControllerAdvice
public class RestControllerAdviceImpl extends ResponseEntityExceptionHandler {
	Logger loggerAdv = LoggerFactory.getLogger(RestControllerAdviceImpl.class);
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ExceptionResponse internalServerError(Exception ex, WebRequest request) {
		loggerAdv.error(ex.getMessage());
		ExceptionResponse er = new ExceptionResponse();
		er.setMessage("Internal Server Error");
		return er;
	}

	@ExceptionHandler(CityNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ExceptionResponse cityNotFoundException(CityNotFoundException ex, WebRequest request) {
		ExceptionResponse er = new ExceptionResponse();
		er.setMessage(ex.getMessage());
		return er;
	}

	@ExceptionHandler(ExternalServiceNotWorking.class)
	@ResponseStatus(value = HttpStatus.BAD_GATEWAY)
	public ExceptionResponse externalServiceNotWorking(ExternalServiceNotWorking ex, WebRequest request) {
		ExceptionResponse er = new ExceptionResponse();
		er.setMessage(ex.getMessage());
		return er;
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		loggerAdv.warn("Returning HTTP 400 Bad Request", ex);
		 ExceptionResponse error = new ExceptionResponse();
		 StringBuilder sb = new StringBuilder();
		 sb.append(ex.getClass().getSimpleName());
		 sb.append(", Request malformed");
		 error.setMessage(sb.toString());

		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		loggerAdv.error("Error validating parameters {}", ex.getMessage());
		List<String> errorList = ex.getBindingResult().getFieldErrors().stream()
				.map(fieldError -> fieldError.getDefaultMessage()).collect(Collectors.toList());
		ExceptionResponse error = new ExceptionResponse();
		error.setMessage(errorList.toString());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
}
