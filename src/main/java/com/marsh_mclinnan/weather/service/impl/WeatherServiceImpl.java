package com.marsh_mclinnan.weather.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import com.marsh_mclinnan.weather.exception.CityNotFoundException;
import com.marsh_mclinnan.weather.gateway.AirPollutionGateway;
import com.marsh_mclinnan.weather.gateway.CitiesGateway;
import com.marsh_mclinnan.weather.gateway.WeatherGateway;
import com.marsh_mclinnan.weather.service.WeatherService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {
	Logger logger = LoggerFactory.getLogger(WeatherServiceImpl.class);
	
	private final CitiesGateway citiesGateway;
	private final AirPollutionGateway airPollutionGateway;
	private final WeatherGateway weatherGateway;

	@Override
	public WeatherForecastDO getForecastWeather(String city){
		logger.info("Requested city {}", city );
		List<CityDO> response = citiesGateway.getCityInfo(city);
		if(!CollectionUtils.isEmpty(response) ){
			CityDO cityInfo = response.get(0);
			return weatherGateway.getForecastWeather(cityInfo.getLat(), cityInfo.getLon());
		}else {
			logger.warn("No city info founded for {}",city);
			throw new CityNotFoundException(String.format("City %s not found", city));
		}
	}

	@Override
	public WeatherAndAirPollutionListDO getWeatherAndPollution(List<String> cities){
		List<String> citiesNotDuplicated = cities.stream()
			.distinct().collect(Collectors.toList());
		
		logger.info("getWeatherAndPollution for cities {}",cities);
		List<CityDO> citiesFounded = new ArrayList<>();
		citiesNotDuplicated.forEach(c -> {
			List<CityDO> citiesExistent = citiesGateway.getCityInfo(c);
			if(!CollectionUtils.isEmpty(citiesExistent)) {
				citiesFounded.add(citiesExistent.get(0));
			}
		});
		
		if(CollectionUtils.isEmpty(citiesFounded)) {
			throw new CityNotFoundException("None city of requested were found");
		}
		
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

		WeatherCurrentDO weather = weatherGateway.getCurrentWeather(city.getLat(), city.getLon());
		weatherAndPollution.setWeather(weather);

		AirPollutionDO pollution = airPollutionGateway.getAirPollutionPort(city.getLat(), city.getLon());
		weatherAndPollution.setAirPollution(pollution);
		return weatherAndPollution;
	}


}
