package com.marsh_mclinnan.weather.gateway.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.marsh_mclinnan.weather.commons.constants.OpenWeatherConstants;
import com.marsh_mclinnan.weather.domain.CityDO;
import com.marsh_mclinnan.weather.exception.ExternalServiceNotWorking;

import java.net.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.marsh_mclinnan.weather.gateway.CitiesGateway;
import com.marsh_mclinnan.weather.gateway.mapper.CityMapper;
import com.marsh_mclinnan.weather.gateway.response.CityInfoRsResponse;
import com.marsh_mclinnan.weather.properties.OpenWeatherProperties;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CitiesGatewayImpl implements CitiesGateway {
	Logger logger = LoggerFactory.getLogger(CitiesGatewayImpl.class);
	
	private final RestTemplate restTemplateCustom;
	private final OpenWeatherProperties opwProperties;
	private final CityMapper mapper;

	@Override
	public List<CityDO> getCityInfo(String cityName){
		logger.info("App key {}",opwProperties.getAppKey());
		
		StringBuilder sb = new StringBuilder();
		sb.append(cityName);
		sb.append(",");
		sb.append(OpenWeatherConstants.US_COUNTRY_CODE);

		URI uri = UriComponentsBuilder.fromHttpUrl(opwProperties.getGeocode().getGeocodeUrl())
				.queryParam(OpenWeatherConstants.QUERY_PARAM_CITY, sb.toString())
				.queryParam(OpenWeatherConstants.QUERY_PARAM_LIMIT, opwProperties.getGeocode().getLimitResults())
				.queryParam(OpenWeatherConstants.QUERY_PARAM_APP_ID, opwProperties.getAppKey())
				.build().toUri() ;

		ResponseEntity<List<CityInfoRsResponse>> response ;
		
		try {
			response = restTemplateCustom.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<CityInfoRsResponse>>(){} );
		}catch(RestClientException ex){
			logger.error("getCityInfo failed");
			logger.error(ex.getMessage());
			throw new ExternalServiceNotWorking("City Geocode service unavailable");
		}

		if(response.hasBody()) {
			return response.getBody().stream()
				.map(mapper::responseToDomain)
				.collect(Collectors.toList());
		}
		
		return Collections.emptyList();
	}
}
