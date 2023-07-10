package com.marsh_mclinnan.weather.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.marsh_mclinnan.weather.domain.AirPollutionDO;
import com.marsh_mclinnan.weather.domain.CityDO;
import com.marsh_mclinnan.weather.domain.WeatherAndAirPollutionDO;
import com.marsh_mclinnan.weather.domain.WeatherAndAirPollutionListDO;
import com.marsh_mclinnan.weather.domain.WeatherCurrentDO;
import com.marsh_mclinnan.weather.domain.WeatherForecastDO;
import com.marsh_mclinnan.weather.gateway.AirPollutionPort;
import com.marsh_mclinnan.weather.gateway.CitiesPort;
import com.marsh_mclinnan.weather.gateway.WeatherPort;
import com.marsh_mclinnan.weather.service.WeatherService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {
	Logger logger = LoggerFactory.getLogger(WeatherServiceImpl.class);
	
	private final CitiesPort citiesPort;
	private final AirPollutionPort airPollutionPort;
	private final WeatherPort weatherPort;

	@Override
	public WeatherForecastDO getForecastWeather(String city) {
		logger.info("Requested city {}", city );
		List<CityDO> response = citiesPort.getCityInfo(city);
		if(!CollectionUtils.isEmpty(response) ){
			CityDO cityInfo = response.get(0);
			return weatherPort.getForecastWeather(cityInfo.getLat(), cityInfo.getLon());
		}
		
		return null;
	}

	@Override
	public WeatherAndAirPollutionListDO getWeatherAndPollution(List<String> cities) {
		logger.info("getWeatherAndPollution for cities {}",cities);
		List<CityDO> citiesFounded = new ArrayList<>();
		cities.forEach(c -> {
			List<CityDO> citiesExistent = citiesPort.getCityInfo(c);
			if(!CollectionUtils.isEmpty(citiesExistent)) {
				citiesFounded.add(citiesExistent.get(0));
			}
		});
		List<WeatherAndAirPollutionDO> weatherList = new ArrayList<>();
		citiesFounded.forEach(c -> {
			weatherList.add(getAirAndWeather(c));
		});
		WeatherAndAirPollutionListDO response = new WeatherAndAirPollutionListDO();
		response.setList(weatherList);
		return response;
	}

	private WeatherAndAirPollutionDO getAirAndWeather(CityDO city) {
		WeatherAndAirPollutionDO weatherAndPollution = new WeatherAndAirPollutionDO();
		weatherAndPollution.setCityName(city.getName());

		WeatherCurrentDO weather = weatherPort.getCurrentWeather(city.getLat(), city.getLon());
		weatherAndPollution.setWeather(weather);

		AirPollutionDO pollution = airPollutionPort.getAirPollutionPort(city.getLat(), city.getLon());
		weatherAndPollution.setAirPollution(pollution);
		return weatherAndPollution;
	}


}
