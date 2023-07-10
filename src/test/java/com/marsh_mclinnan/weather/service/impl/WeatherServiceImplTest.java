package com.marsh_mclinnan.weather.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.javafaker.Faker;
import com.marsh_mclinnan.weather.domain.CityDO;
import com.marsh_mclinnan.weather.domain.WeatherForecastDO;
import com.marsh_mclinnan.weather.gateway.AirPollutionPort;
import com.marsh_mclinnan.weather.gateway.CitiesPort;
import com.marsh_mclinnan.weather.gateway.WeatherPort;
import com.marsh_mclinnan.weather.service.WeatherService;

@ExtendWith(MockitoExtension.class)
class WeatherServiceImplTest {
	private final String LAT = "34.1476452";
	private final String LON = "-118.1444779";
	
	Faker faker = new Faker();

	@Mock
	private CitiesPort citiesPort;
	@Mock
	private AirPollutionPort airPollutionPort;
	@Mock
	private WeatherPort weatherPort;
	
	private WeatherService service;

	@BeforeEach
	void setUp() {
		service = new WeatherServiceImpl(citiesPort, airPollutionPort, weatherPort);
	}

	@Test
	void getForecastWeather_noResults_success() {
		when(citiesPort.getCityInfo(anyString())).thenReturn(List.of());
		WeatherForecastDO forecast = service.getForecastWeather(faker.address().city());
		assertThat(forecast).isNull();
	}

	@Test
	void getForecastWeather_oneResults_success() {
		String cityName = faker.address().city();
		CityDO city = CityDO.builder()
			.lat(new BigDecimal(LAT))
			.lon(new BigDecimal(LON))
			.name(cityName)
			.build();
		
		when(citiesPort.getCityInfo(anyString())).thenReturn(List.of(city));
		WeatherForecastDO forecast = service.getForecastWeather(faker.address().city());
		assertThat(forecast).isNull();
	}

	
}
