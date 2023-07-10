package com.marsh_mclinnan.weather.domain;

import java.time.Instant;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WeatherForecastDO {
	private Integer cnt;
	private List<WeatherCurrentDO> list;
	private Instant sunrise;
	private Instant sunset;
}