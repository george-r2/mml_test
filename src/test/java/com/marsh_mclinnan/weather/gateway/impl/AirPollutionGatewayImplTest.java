package com.marsh_mclinnan.weather.gateway.impl;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.marsh_mclinnan.weather.commons.constants.OpenWeatherConstants;
import com.marsh_mclinnan.weather.domain.AirPollutionDO;
import com.marsh_mclinnan.weather.gateway.AirPollutionGateway;
import com.marsh_mclinnan.weather.gateway.mapper.AirPollutionMapper;
import com.marsh_mclinnan.weather.properties.OpenWeatherProperties;
import com.marsh_mclinnan.weather.properties.OpenWeatherProperties.AirPollution;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class AirPollutionGatewayImplTest {
	private final String LAT = "34.1476452";
	private final String LON = "-118.1444779";
	private final String AIR_POLLUTION_URL = "http://localhost:8080/air_pollution";
	
	private final String responseJson = "{\"coord\":{\"lon\":-118.7414,\"lat\":34.0537},\"list\":[{\"main\":{\"aqi\":2},"
			+ "\"components\":{\"co\":220.3,\"no\":0,\"no2\":8.31,\"o3\":67.95,\"so2\":1.71,\"pm2_5\":4.26,\"pm10\":7.22,\"nh3\":0.47},"
			+ "\"dt\":1688965399}]}";

	@Autowired
	private RestTemplate restTemplateCustom;
	@Mock
	private OpenWeatherProperties opwProperties;
	
	private AirPollutionGateway airPollutionGateway;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	private AirPollutionMapper mapper;
	
    private MockRestServiceServer mockServer;
	
	@BeforeEach
	void setUp() {
		mockServer = MockRestServiceServer.createServer(restTemplateCustom);
		
		AirPollution ar = new AirPollution();
		ar.setAirPollutionUrl(AIR_POLLUTION_URL);
		when(opwProperties.getAirPollution()).thenReturn(ar);
		when(opwProperties.getAppKey()).thenReturn("123456");
		
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		mapper = Mappers.getMapper(AirPollutionMapper.class);
		airPollutionGateway = new AirPollutionGatewayImpl(restTemplateCustom, opwProperties, mapper);
	}

	@Test
	void getAirPollutionPort_success() throws IOException, URISyntaxException {
		URI uri = UriComponentsBuilder.fromHttpUrl(opwProperties.getAirPollution().getAirPollutionUrl())
				.queryParam(OpenWeatherConstants.QUERY_PARAM_LAT, LAT)
				.queryParam(OpenWeatherConstants.QUERY_PARAM_LON, LON)
				.queryParam(OpenWeatherConstants.QUERY_PARAM_APP_ID, opwProperties.getAppKey())
				.build().toUri() ;
		
		 mockServer.expect(ExpectedCount.once(), 
			requestTo(uri))
			.andExpect(method(HttpMethod.GET))
			.andRespond(withStatus(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(responseJson));
		 
		AirPollutionDO result = airPollutionGateway.getAirPollutionPort(new BigDecimal(LAT), new BigDecimal(LON));
		assertThat(result).isNotNull();
	}
}
