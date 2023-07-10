package com.marsh_mclinnan.weather.gateway;

import java.math.BigDecimal;

import com.marsh_mclinnan.weather.domain.WeatherCurrentDO;
import com.marsh_mclinnan.weather.domain.WeatherForecastDO;

public interface WeatherPort {

	WeatherCurrentDO getCurrentWeather(BigDecimal lat, BigDecimal lon);

	WeatherForecastDO getForecastWeather(BigDecimal lat, BigDecimal lon);
}
