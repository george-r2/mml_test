package com.marsh_mclinnan.weather.domain;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WeatherMainDailyDO {
	private BigDecimal temp;
	private BigDecimal feelsLike;
	private BigDecimal pressure;
	private BigDecimal humidity;
}
