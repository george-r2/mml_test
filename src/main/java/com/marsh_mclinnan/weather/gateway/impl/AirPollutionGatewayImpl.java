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
import com.marsh_mclinnan.weather.domain.AirPollutionDO;
import com.marsh_mclinnan.weather.gateway.AirPollutionPort;
import com.marsh_mclinnan.weather.gateway.mapper.AirPollutionMapper;
import com.marsh_mclinnan.weather.gateway.response.AirPollutionRsResponse;
import com.marsh_mclinnan.weather.properties.OpenWeatherProperties;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AirPollutionGatewayImpl implements AirPollutionPort {
	Logger logger = LoggerFactory.getLogger(AirPollutionGatewayImpl.class);

	private final OpenWeatherProperties opwProperties;
	private final AirPollutionMapper mapper;

	@Override
	public AirPollutionDO getAirPollutionPort(BigDecimal lat, BigDecimal lon) {
		logger.info("getAirPollutionPort invoked");
		RestTemplate restTemplate = new RestTemplate();
		
		URI uri = UriComponentsBuilder.fromHttpUrl(opwProperties.getAirPollution().getAirPollutionUrl())
				.queryParam(OpenWeatherConstants.QUERY_PARAM_LAT, lat)
				.queryParam(OpenWeatherConstants.QUERY_PARAM_LON, lon)
				.queryParam(OpenWeatherConstants.QUERY_PARAM_APP_ID, opwProperties.getAppKey())
				.build().toUri() ;
		
		ResponseEntity<AirPollutionRsResponse> response 
			= restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<AirPollutionRsResponse>(){} ) ;
	 
		return mapper.responseToDomain(response.getBody());
	}
}
