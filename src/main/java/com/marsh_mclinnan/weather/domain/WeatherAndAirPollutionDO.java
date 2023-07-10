package com.marsh_mclinnan.weather.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WeatherAndAirPollutionDO {

	private String cityName;
	private WeatherCurrentDO weather;
	private AirPollutionDO airPollution;
}
