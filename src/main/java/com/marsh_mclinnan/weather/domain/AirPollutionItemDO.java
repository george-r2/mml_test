package com.marsh_mclinnan.weather.domain;

import java.time.Instant;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AirPollutionItemDO {

	private AirPollutionMainDO main;
	private AirPollutionComponentsDO components;
	private Instant dt;
}