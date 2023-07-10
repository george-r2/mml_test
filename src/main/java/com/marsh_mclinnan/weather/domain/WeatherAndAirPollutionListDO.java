package com.marsh_mclinnan.weather.domain;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WeatherAndAirPollutionListDO {

	private List<WeatherAndAirPollutionDO> list;
}
