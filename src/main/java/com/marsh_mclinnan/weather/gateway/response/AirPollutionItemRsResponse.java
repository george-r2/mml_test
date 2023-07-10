package com.marsh_mclinnan.weather.gateway.response;

import java.time.Instant;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AirPollutionItemRsResponse {

	private AirPollutionMainRsResponse main;
	private AirPollutionComponentsRsResponse components;
	private Instant dt;
}
