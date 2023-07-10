package com.marsh_mclinnan.weather.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WeatherSummaryDO {
	private String main;
	private String description;
}
