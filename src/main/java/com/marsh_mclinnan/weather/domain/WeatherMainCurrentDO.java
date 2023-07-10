package com.marsh_mclinnan.weather.domain;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WeatherMainCurrentDO {

	private BigDecimal temp;
	private BigDecimal feelsLike;
	private BigDecimal tempMin;
	private BigDecimal tempMax;
	private BigDecimal pressure;
	private BigDecimal humidity;
}
