package com.marsh_mclinnan.weather.gateway.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import java.net.URI;
import java.util.List;

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
import com.marsh_mclinnan.weather.domain.CityDO;
import com.marsh_mclinnan.weather.gateway.CitiesGateway;
import com.marsh_mclinnan.weather.gateway.mapper.CityMapper;
import com.marsh_mclinnan.weather.properties.OpenWeatherProperties;
import com.marsh_mclinnan.weather.properties.OpenWeatherProperties.GeoCodeProperties;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class CitiesGatewayImplTest {
	private final String CITY_NAME = "PASADENA";
	private final String GEOCODE_URL = "http://localhost:8080/air_pollution";
	
	private final String responseJson = "[{\"name\":\"Pasadena\",\"local_names\":{\"en\":"
			+ "\"Pasadena\"},\"lat\":34.1476452,\"lon\":-118.1444779,\"country\":\"US\",\"state\":\"California\"},{\"name\":"
			+ "\"Pasadena\",\"local_names\":{\"en\":\"Pasadena\"},\"lat\":29.6910753,\"lon\":-95.2092076,"
			+ "\"country\":\"US\",\"state\":\"Texas\"}]";

	@Autowired
	private RestTemplate restTemplateCustom;
	@Mock
	private OpenWeatherProperties opwProperties;
	
	private CitiesGateway citiesGateway;
	private ObjectMapper objectMapper = new ObjectMapper();
	private CityMapper mapper;
	
	private MockRestServiceServer mockServer;
	
	@BeforeEach
	void setUp() {
		mockServer = MockRestServiceServer.createServer(restTemplateCustom);
		
		GeoCodeProperties gcp = new GeoCodeProperties();
		gcp.setGeocodeUrl(GEOCODE_URL);
		when(opwProperties.getGeocode()).thenReturn(gcp);
		when(opwProperties.getAppKey()).thenReturn("123456");
		
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		mapper = Mappers.getMapper(CityMapper.class);
		citiesGateway = new CitiesGatewayImpl(restTemplateCustom, opwProperties, mapper);
	}
	
	
	@Test
	void getCityInfo_success() {
		URI uri = UriComponentsBuilder.fromHttpUrl(opwProperties.getGeocode().getGeocodeUrl())
				.queryParam(OpenWeatherConstants.QUERY_PARAM_CITY, CITY_NAME + "," + OpenWeatherConstants.US_COUNTRY_CODE)
				.queryParam(OpenWeatherConstants.QUERY_PARAM_LIMIT, opwProperties.getGeocode().getLimitResults())
				.queryParam(OpenWeatherConstants.QUERY_PARAM_APP_ID, opwProperties.getAppKey())
				.build().toUri() ;
		
		 mockServer.expect(ExpectedCount.once(), 
			requestTo(uri))
			.andExpect(method(HttpMethod.GET))
			.andRespond(withStatus(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(responseJson));
		
		 List<CityDO> cities = citiesGateway.getCityInfo(CITY_NAME);
		 assertThat(cities).isNotNull().isNotEmpty();
	}
}
