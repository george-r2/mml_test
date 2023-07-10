package com.marsh_mclinnan.weather.domain;

import java.time.Instant;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WeatherSysDO {

	private Instant sunrise;
	private Instant sunset;
	private String sys;
}
