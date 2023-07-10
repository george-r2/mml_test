package com.marsh_mclinnan.weather.controller;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marsh_mclinnan.weather.domain.WeatherAndAirPollutionListDO;
import com.marsh_mclinnan.weather.domain.WeatherForecastDO;
import com.marsh_mclinnan.weather.exception.MMLException;
import com.marsh_mclinnan.weather.service.WeatherService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor
@Validated
public class WeatherController {
	private final WeatherService weatherService;

	Logger logger = LoggerFactory.getLogger(WeatherController.class);

	@GetMapping("/current")
	public WeatherAndAirPollutionListDO getWeatherInfo(
				@Size(max = 5, message = "Due API limitations only 5 cities are allowed by request")
				@NotEmpty(message="Cities list cannot be empty")
				@RequestParam("cities") List<@NotBlank String> cities) throws MMLException{
		logger.info("Requested cities {}", cities );
		return weatherService.getWeatherAndPollution(cities);
	}

	@GetMapping("/forecast")
	public WeatherForecastDO getWeatherForecast(
			@NotEmpty(message="City param cannot be empty") @Size(min=1,max=100) @RequestParam("city") String city) throws MMLException {
		logger.info("Requested city {}", city );
		return weatherService.getForecastWeather(city);
	}
}
