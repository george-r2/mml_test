package com.marsh_mclinnan.weather.gateway.impl;

import java.math.BigDecimal;
import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.marsh_mclinnan.weather.commons.constants.OpenWeatherConstants;
import com.marsh_mclinnan.weather.domain.WeatherCurrentDO;
import com.marsh_mclinnan.weather.domain.WeatherForecastDO;
import com.marsh_mclinnan.weather.gateway.WeatherPort;
import com.marsh_mclinnan.weather.gateway.mapper.WeatherMapper;
import com.marsh_mclinnan.weather.gateway.response.WeatherCurrentRsResponse;
import com.marsh_mclinnan.weather.gateway.response.WeatherForecastRsResponse;
import com.marsh_mclinnan.weather.properties.OpenWeatherProperties;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WeatherGatewayImpl implements WeatherPort {
	Logger logger = LoggerFactory.getLogger(WeatherGatewayImpl.class);

	private final OpenWeatherProperties opwProperties;
	private final WeatherMapper mapper;

	@Override
	public WeatherCurrentDO getCurrentWeather(BigDecimal lat, BigDecimal lon) {
		logger.info("getCurrentWeather");

		RestTemplate restTemplate = new RestTemplate();
		
		URI uri = UriComponentsBuilder.fromHttpUrl(opwProperties.getWeather().getCurrentUrl())
				.queryParam(OpenWeatherConstants.QUERY_PARAM_LAT, lat)
				.queryParam(OpenWeatherConstants.QUERY_PARAM_LON, lon)
				.queryParam(OpenWeatherConstants.QUERY_PARAM_UNITS, OpenWeatherConstants.QUERY_PARAMS_UNITS_METRIC)
				.queryParam(OpenWeatherConstants.QUERY_PARAM_APP_ID, opwProperties.getAppKey())
				.build().toUri() ;

		ResponseEntity<WeatherCurrentRsResponse> response 
			= restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<WeatherCurrentRsResponse>(){} ) ;
		
		return mapper.responseToDomain(response.getBody());
	}

	@Override
	public WeatherForecastDO getForecastWeather(BigDecimal lat, BigDecimal lon) {
		logger.info("getForecastWeather");

		RestTemplate restTemplate = new RestTemplate();
		
		URI uri = UriComponentsBuilder.fromHttpUrl(opwProperties.getWeather().getForecastUrl())
				.queryParam(OpenWeatherConstants.QUERY_PARAM_LAT, lat)
				.queryParam(OpenWeatherConstants.QUERY_PARAM_LON, lon)
				.queryParam(OpenWeatherConstants.QUERY_PARAM_UNITS, OpenWeatherConstants.QUERY_PARAMS_UNITS_METRIC)
				.queryParam(OpenWeatherConstants.QUERY_PARAM_APP_ID, opwProperties.getAppKey())
				.build().toUri() ;

		ResponseEntity<WeatherForecastRsResponse> response 
			= restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<WeatherForecastRsResponse>(){} ) ;
		
		return mapper.responseToDomain(response.getBody());
	}
}
