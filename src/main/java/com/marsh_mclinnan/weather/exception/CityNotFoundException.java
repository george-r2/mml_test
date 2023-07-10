package com.marsh_mclinnan.weather.exception;

public class CityNotFoundException extends MMLException{

	private static final long serialVersionUID = 636777568252598576L;

	public CityNotFoundException(String msg) {
		super(msg);
	}
}
