package com.marsh_mclinnan.weather.gateway.response;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WeatherCoordRsResponse {

	private BigDecimal lat;
	private BigDecimal lon;
}
