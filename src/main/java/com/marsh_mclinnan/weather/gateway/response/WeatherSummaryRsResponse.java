package com.marsh_mclinnan.weather.gateway.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WeatherSummaryRsResponse {
	private Integer id;
	private String main;
	private String description;
}
