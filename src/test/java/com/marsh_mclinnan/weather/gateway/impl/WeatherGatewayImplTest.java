package com.marsh_mclinnan.weather.gateway.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

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
import com.marsh_mclinnan.weather.domain.WeatherCurrentDO;
import com.marsh_mclinnan.weather.domain.WeatherForecastDO;
import com.marsh_mclinnan.weather.gateway.WeatherGateway;
import com.marsh_mclinnan.weather.gateway.mapper.WeatherMapper;
import com.marsh_mclinnan.weather.properties.OpenWeatherProperties;
import com.marsh_mclinnan.weather.properties.OpenWeatherProperties.Weather;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class WeatherGatewayImplTest {
	private final String LAT = "34.1476452";
	private final String LON = "-118.1444779";
	private final String WEATHER_FORECAST_URL = "http://localhost:8080/forecast";
	private final String WEATHER_CURRENT_URL = "http://localhost:8080/current";
	private final String APP_ID = "123456";
	
	private final String responseForecastJson = "{\"cod\":\"200\",\"message\":0,\"cnt\":4,\"list\":[{\"dt\":1688979600,\"main\":{\"temp\":301.78,"
			+ "\"feels_like\":306.13,\"temp_min\":301.78,\"temp_max\":302.24,\"pressure\":1014,\"sea_level\":1014,\"grnd_level\":1014,"
			+ "\"humidity\":76,\"temp_kf\":-0.46},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"brokenclouds\","
			+ "\"icon\":\"04n\"}],\"clouds\":{\"all\":60},\"wind\":{\"speed\":5.23,\"deg\":243,\"gust\":6.11},\"visibility\":10000,"
			+ "\"pop\":0,\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2023-07-1009:00:00\"},{\"dt\":1688990400,\"main\":{\"temp\":302.18,"
			+ "\"feels_like\":306.72,\"temp_min\":302.18,\"temp_max\":302.49,\"pressure\":1015,\"sea_level\":1015,\"grnd_level\":1015,"
			+ "\"humidity\":74,\"temp_kf\":-0.31},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"brokenclouds\","
			+ "\"icon\":\"04d\"}],\"clouds\":{\"all\":80},\"wind\":{\"speed\":5.85,\"deg\":242,\"gust\":6.5},\"visibility\":10000,"
			+ "\"pop\":0,\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-07-1012:00:00\"},{\"dt\":1689001200,\"main\":{\"temp\":303.49,"
			+ "\"feels_like\":308.02,\"temp_min\":303.49,\"temp_max\":303.49,\"pressure\":1017,\"sea_level\":1017,\"grnd_level\":1016,"
			+ "\"humidity\":66,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcastclouds\","
			+ "\"icon\":\"04d\"}],\"clouds\":{\"all\":96},\"wind\":{\"speed\":5.91,\"deg\":251,\"gust\":6.49},"
			+ "\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-07-1015:00:00\"},{\"dt\":1689012000,"
			+ "\"main\":{\"temp\":303.78,\"feels_like\":308.7,\"temp_min\":303.78,\"temp_max\":303.78,\"pressure\":1017,"
			+ "\"sea_level\":1017,\"grnd_level\":1016,\"humidity\":66,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\","
			+ "\"description\":\"lightrain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":98},\"wind\":{\"speed\":6.03,\"deg\":255,\"gust\":6.59},"
			+ "\"visibility\":10000,\"pop\":0.54,\"rain\":{\"3h\":1},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-07-1018:00:00\"}],\"city\":{\"id\":4151316,"
			+ "\"name\":\"Clearwater\",\"coord\":{\"lat\":27.9659,\"lon\":-82.8001},\"country\":\"US\",\"population\":107685,\"timezone\":-14400,\"sunrise\":1688985719,\"sunset\":1689035440}}";

	private final String responseCurrentJson = "{\"coord\":{\"lon\":-82.8001,\"lat\":27.9659},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scatteredclouds\",\"icon\":\"03n\"}],"
			+ "\"base\":\"stations\",\"main\":{\"temp\":28.4,\"feels_like\":33.12,\"temp_min\":25.98,\"temp_max\":29.4,\"pressure\":1014,\"humidity\":80},\"visibility\":10000,\"wind\":{\"speed\":6.71,"
			+ "\"deg\":360,\"gust\":8.49},\"clouds\":{\"all\":40},\"dt\":1688972501,\"sys\":{\"type\":2,\"id\":2004270,\"country\":\"US\",\"sunrise\":1688985719,\"sunset\":1689035440},"
			+ "\"timezone\":-14400,\"id\":4151316,\"name\":\"Clearwater\",\"cod\":200}";

	@Autowired
	private RestTemplate restTemplateCustom;
	@Mock
	private OpenWeatherProperties opwProperties;
	
	private WeatherGateway weatherGateway;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	private WeatherMapper mapper;
	private MockRestServiceServer mockServer;
	
	@BeforeEach
	void setUp() {
		mockServer = MockRestServiceServer.createServer(restTemplateCustom);
		
		Weather weat = new Weather();
		weat.setCurrentUrl(WEATHER_CURRENT_URL);
		weat.setForecastUrl(WEATHER_FORECAST_URL);
		when(opwProperties.getWeather()).thenReturn(weat);
		when(opwProperties.getAppKey()).thenReturn(APP_ID);
		
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		mapper = Mappers.getMapper(WeatherMapper.class);
		weatherGateway = new WeatherGatewayImpl(restTemplateCustom, opwProperties, mapper);
	}
	
	@Test
	void getCurrentWeather_sucess() throws IOException, URISyntaxException {
		URI uri = getURI(WEATHER_CURRENT_URL, LAT, LON, APP_ID);
		
		 mockServer.expect(ExpectedCount.once(), 
					requestTo(uri))
					.andExpect(method(HttpMethod.GET))
					.andRespond(withStatus(HttpStatus.OK)
					.contentType(MediaType.APPLICATION_JSON)
					.body(responseCurrentJson));
		 
		 WeatherCurrentDO current = weatherGateway.getCurrentWeather(new BigDecimal(LAT), new BigDecimal(LON));
		 assertThat(current).isNotNull();
	}
	
	@Test
	void getForecastWeather_sucess() throws IOException, URISyntaxException {
		URI uri = getURI(WEATHER_FORECAST_URL, LAT, LON, APP_ID);
		
		mockServer.expect(ExpectedCount.once(), 
					requestTo(uri))
					.andExpect(method(HttpMethod.GET))
					.andRespond(withStatus(HttpStatus.OK)
					.contentType(MediaType.APPLICATION_JSON)
					.body(responseForecastJson));
		
		WeatherForecastDO forecast = weatherGateway.getForecastWeather(new BigDecimal(LAT), new BigDecimal(LON));
		assertThat(forecast).isNotNull();
	}
	
	private URI getURI(String url, String lat, String lon, String appId) {
		URI uri = UriComponentsBuilder.fromHttpUrl(url)
				.queryParam(OpenWeatherConstants.QUERY_PARAM_LAT, lat)
				.queryParam(OpenWeatherConstants.QUERY_PARAM_LON, lon)
				.queryParam(OpenWeatherConstants.QUERY_PARAM_UNITS, OpenWeatherConstants.QUERY_PARAMS_UNITS_METRIC)
				.queryParam(OpenWeatherConstants.QUERY_PARAM_APP_ID, appId)
				.build().toUri() ;
		return uri;
	}
}
