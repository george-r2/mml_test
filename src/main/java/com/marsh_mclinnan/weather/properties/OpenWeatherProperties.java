package com.marsh_mclinnan.weather.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Component
@ConfigurationProperties(prefix = "opw")
public class OpenWeatherProperties {
	
	private String appKey;
	private GeoCodeProperties geocode;
	private AirPollution airPollution;
	private Weather weather;

	@Data
	@NoArgsConstructor
	public static class GeoCodeProperties{
		private static final int DEFAULT_LIMIT = 1;
		
		private String geocodeUrl;
		private Integer limitResults = DEFAULT_LIMIT;
	}

	@Data
	@NoArgsConstructor
	public static class AirPollution{
		private String airPollutionUrl;
	}

	@Data
	@NoArgsConstructor
	public static class Weather{
		private String currentUrl;
		private String forecastUrl;
	}
}
