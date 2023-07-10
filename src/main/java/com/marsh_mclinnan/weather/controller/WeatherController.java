package com.marsh_mclinnan.weather.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marsh_mclinnan.weather.domain.WeatherAndAirPollutionListDO;
import com.marsh_mclinnan.weather.domain.WeatherForecastDO;
import com.marsh_mclinnan.weather.service.WeatherService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor
public class WeatherController {
	private final WeatherService weatherService;

	Logger logger = LoggerFactory.getLogger(WeatherController.class);

	@GetMapping("/current")
	public WeatherAndAirPollutionListDO getWeatherInfo(
			@Valid @NotEmpty @RequestParam("cities") List<String> cities) {
		logger.info("Requested cities {}", cities );
		return weatherService.getWeatherAndPollution(cities);
	}

	@GetMapping("/forecast")
	public WeatherForecastDO getWeatherForecast(
			@Valid @NotEmpty @RequestParam("city") String city) {
		logger.info("Requested city {}", city );
		return weatherService.getForecastWeather(city);
	}
}
