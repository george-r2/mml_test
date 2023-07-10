package com.marsh_mclinnan.weather.gateway.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WeatherWindRsResponse {

	private Double speed;
	private Double deg;
	private Double gust;
}
