package com.marsh_mclinnan.weather.gateway.impl;

import java.math.BigDecimal;
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

import com.marsh_mclinnan.weather.commons.constants.OpenWeatherConstants;
import com.marsh_mclinnan.weather.domain.AirPollutionDO;
import com.marsh_mclinnan.weather.exception.ExternalServiceNotWorking;
import com.marsh_mclinnan.weather.exception.MMLException;
import com.marsh_mclinnan.weather.gateway.AirPollutionGateway;
import com.marsh_mclinnan.weather.gateway.mapper.AirPollutionMapper;
import com.marsh_mclinnan.weather.gateway.response.AirPollutionRsResponse;
import com.marsh_mclinnan.weather.properties.OpenWeatherProperties;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AirPollutionGatewayImpl implements AirPollutionGateway {
	Logger logger = LoggerFactory.getLogger(AirPollutionGatewayImpl.class);

	private final RestTemplate restTemplateCustom;
	private final OpenWeatherProperties opwProperties;
	private final AirPollutionMapper mapper;

	@Override
	public AirPollutionDO getAirPollutionPort(BigDecimal lat, BigDecimal lon) throws MMLException {
		logger.info("getAirPollutionPort invoked");
		URI uri = UriComponentsBuilder.fromHttpUrl(opwProperties.getAirPollution().getAirPollutionUrl())
				.queryParam(OpenWeatherConstants.QUERY_PARAM_LAT, lat)
				.queryParam(OpenWeatherConstants.QUERY_PARAM_LON, lon)
				.queryParam(OpenWeatherConstants.QUERY_PARAM_APP_ID, opwProperties.getAppKey())
				.build().toUri() ;
		try{
			ResponseEntity<AirPollutionRsResponse> response 
			 	= restTemplateCustom.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<AirPollutionRsResponse>(){} ) ;
			return mapper.responseToDomain(response.getBody());
		}catch(RestClientException ex) {
			logger.error("getAirPollutionPort failed");
			logger.error(ex.getMessage());
			throw new ExternalServiceNotWorking("AirPollution service unavailable");
		}
	}
}
