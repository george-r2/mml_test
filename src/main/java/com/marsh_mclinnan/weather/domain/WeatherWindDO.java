package com.marsh_mclinnan.weather.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WeatherWindDO {

	private Double speed;
	private Double deg;
	private Double gust;
}
