package com.marsh_mclinnan.weather.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.javafaker.Faker;
import com.marsh_mclinnan.weather.domain.AirPollutionDO;
import com.marsh_mclinnan.weather.domain.CityDO;
import com.marsh_mclinnan.weather.domain.WeatherAndAirPollutionListDO;
import com.marsh_mclinnan.weather.domain.WeatherCurrentDO;
import com.marsh_mclinnan.weather.domain.WeatherForecastDO;
import com.marsh_mclinnan.weather.exception.CityNotFoundException;
import com.marsh_mclinnan.weather.gateway.AirPollutionGateway;
import com.marsh_mclinnan.weather.gateway.CitiesGateway;
import com.marsh_mclinnan.weather.gateway.WeatherGateway;
import com.marsh_mclinnan.weather.service.WeatherService;

@ExtendWith(MockitoExtension.class)
class WeatherServiceImplTest {
	private final String LAT = "34.1476452";
	private final String LON = "-118.1444779";
	
	Faker faker = new Faker();

	@Mock
	private CitiesGateway citiesGateway;
	@Mock
	private AirPollutionGateway airPollutionGateway;
	@Mock
	private WeatherGateway weatherGateway;
	
	private WeatherService service;

	@BeforeEach
	void setUp() {
		service = new WeatherServiceImpl(citiesGateway, airPollutionGateway, weatherGateway);
	}

	@Test
	void getForecastWeather_noResults_success() {
		when(citiesGateway.getCityInfo(anyString())).thenReturn(List.of());
		Throwable cityNotFound = catchThrowable(() -> service.getForecastWeather(faker.address().city()));
		AssertionsForClassTypes.assertThat(cityNotFound).isInstanceOf(CityNotFoundException.class);
	}

	@Test
	void getForecastWeather_oneResults_success() {
		String cityName = faker.address().city();
		CityDO city = CityDO.builder()
			.lat(new BigDecimal(LAT))
			.lon(new BigDecimal(LON))
			.name(cityName)
			.build();
		
		when(citiesGateway.getCityInfo(anyString())).thenReturn(List.of(city));
		WeatherForecastDO forecast = service.getForecastWeather(faker.address().city());
		assertThat(forecast).isNull();
	}

	@Test
	void getWeatherAndPollution_noCitiesFound_error() {
		when(citiesGateway.getCityInfo(anyString())).thenReturn(List.of());
		Throwable cityNotFound = catchThrowable(() -> service.getWeatherAndPollution(List.of(faker.address().city())));
		AssertionsForClassTypes.assertThat(cityNotFound).isInstanceOf(CityNotFoundException.class);
	}

	@Test
	void getWeatherAndPollution_oneResults_success() {
		String cityName = faker.address().city();
		CityDO city = CityDO.builder()
			.lat(new BigDecimal(LAT))
			.lon(new BigDecimal(LON))
			.name(cityName)
			.build();
		
		when(citiesGateway.getCityInfo(anyString())).thenReturn(List.of(city));
		when(weatherGateway.getCurrentWeather(any(), any())).thenReturn(new WeatherCurrentDO());
		when(airPollutionGateway.getAirPollutionPort(any(), any())).thenReturn(new AirPollutionDO());
		
		WeatherAndAirPollutionListDO response = service.getWeatherAndPollution(List.of(faker.address().city()));
		assertThat(response).isNotNull();
	}
}
