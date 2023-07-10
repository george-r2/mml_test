package com.marsh_mclinnan.weather.gateway.response;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WeatherCurrentRsResponse {

	private WeatherCoordRsResponse coord;
	private List<WeatherSummaryRsResponse> weather;
	private String base;
	private WeatherMainRsResponse main;
	private Long visibility;
	private WeatherWindRsResponse wind;
	private WeatherCloudRsResponse clouds;
	private WeatherSysRsResponse sys;
	private WeatherRainRsResponse rain;
	private Instant dt;
	private Double pop;
	private String name;
}
