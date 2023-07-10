package com.marsh_mclinnan.weather.gateway.response;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WeatherMainRsResponse {

	private BigDecimal temp;
	@JsonProperty("feels_like")
	private BigDecimal feelsLike;
	@JsonProperty("temp_min")
	private BigDecimal tempMin;
	@JsonProperty("temp_max")
	private BigDecimal tempMax;
	private BigDecimal pressure;
	private BigDecimal humidity;
}
