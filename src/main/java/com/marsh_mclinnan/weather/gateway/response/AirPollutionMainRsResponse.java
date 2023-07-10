package com.marsh_mclinnan.weather.gateway.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AirPollutionMainRsResponse {

	@JsonProperty("aqi")
	private Integer airQualityIndex; 
}
