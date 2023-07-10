package com.marsh_mclinnan.weather.gateway.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AirPollutionComponentsRsResponse {

	@JsonProperty("co")
	private Double carbonMonoxide;

	@JsonProperty("no")
	private Double nitrogenMonoxide;

	@JsonProperty("no2")
	private Double nitrogenDioxide;

	@JsonProperty("o3")
	private Double ozone;

	@JsonProperty("so2")
	private Double sulphurDioxide;

	@JsonProperty("pm2_5")
	private Double fineParticlesMatter;

	@JsonProperty("pm10")
	private Double coarseParticulateMatter;

	@JsonProperty("nh3")
	private Double ammonia;
}