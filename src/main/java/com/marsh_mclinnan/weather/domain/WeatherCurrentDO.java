package com.marsh_mclinnan.weather.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherCurrentDO {

	private List<WeatherSummaryDO> weather;
	private WeatherMainCurrentDO main;
	private Long visibility;
	private WeatherWindDO wind;
	private WeatherSysDO sys;
	private String name;
	private Integer probabilityOfPrecipitation;
}
