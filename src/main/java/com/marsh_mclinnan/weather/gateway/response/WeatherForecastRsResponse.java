package com.marsh_mclinnan.weather.gateway.response;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WeatherForecastRsResponse {
	private Integer cnt;
	private List<WeatherCurrentRsResponse> list;
	private Instant sunrise;
	private Instant sunset;
}
