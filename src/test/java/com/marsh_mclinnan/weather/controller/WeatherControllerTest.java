package com.marsh_mclinnan.weather.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.marsh_mclinnan.weather.config.SecurityConfig;
import com.marsh_mclinnan.weather.domain.WeatherAndAirPollutionListDO;
import com.marsh_mclinnan.weather.domain.WeatherForecastDO;
import com.marsh_mclinnan.weather.service.WeatherService;

@WebMvcTest(controllers = {WeatherController.class}, excludeAutoConfiguration = {SecurityConfig.class})
@AutoConfigureMockMvc(addFilters = false)
class WeatherControllerTest {
	
	private final String CURRENT_URL = "/weather/current";
	private final String FORECAST_URL = "/weather/forecast";
	
	private final String CITIES_PATH_PARAM = "cities";
	private final String CITY_PATH_PARAM = "city";

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private WeatherService weatherService;
	
	@Test
	void getCurrentWeatherInfo_success() throws Exception {
		when(weatherService.getWeatherAndPollution(any())).thenReturn(new WeatherAndAirPollutionListDO());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				CURRENT_URL).accept(MediaType.APPLICATION_JSON).queryParam(CITIES_PATH_PARAM, "aaa");
		
		mockMvc.perform(requestBuilder)
			.andExpect(status().is2xxSuccessful())
			.andReturn();
	}
	
	@Test
	void getForecastWeatherInfo_success() throws Exception {
		when(weatherService.getForecastWeather(any())).thenReturn(new WeatherForecastDO());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				FORECAST_URL).accept(MediaType.APPLICATION_JSON).queryParam(CITY_PATH_PARAM, "aaa");
		
		mockMvc.perform(requestBuilder)
			.andExpect(status().is2xxSuccessful())
			.andReturn();
	}
}
