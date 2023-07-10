package com.marsh_mclinnan.weather.service;

import java.util.List;

import com.marsh_mclinnan.weather.domain.WeatherAndAirPollutionListDO;
import com.marsh_mclinnan.weather.domain.WeatherForecastDO;

public interface WeatherService {

	WeatherAndAirPollutionListDO getWeatherAndPollution(List<String> cities);

	WeatherForecastDO getForecastWeather(String city);
}
