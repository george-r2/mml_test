package com.marsh_mclinnan.weather.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CurrentWeatherPollutionDO {

	private AirPollutionItemDO airPollution;
}
