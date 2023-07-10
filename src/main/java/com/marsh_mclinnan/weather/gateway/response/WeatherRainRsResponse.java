package com.marsh_mclinnan.weather.gateway.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WeatherRainRsResponse {

	@JsonProperty("1h")
	private Double oneHour;
	@JsonProperty("3h")
	private Double threeHours;
}
