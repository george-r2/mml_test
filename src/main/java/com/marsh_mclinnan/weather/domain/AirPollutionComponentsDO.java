package com.marsh_mclinnan.weather.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AirPollutionComponentsDO {

	private Double carbonMonoxide;
	private Double nitrogenMonoxide;
	private Double nitrogenDioxide;
	private Double ozone;
	private Double sulphurDioxide;
	private Double fineParticlesMatter;
	private Double coarseParticulateMatter;
	private Double ammonia;
}
