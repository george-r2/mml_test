package com.marsh_mclinnan.weather.gateway.response;

import java.time.Instant;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WeatherSysRsResponse {

	private Instant sunrise;
	private Instant sunset;
	private String sys;
}
