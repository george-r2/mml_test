package com.marsh_mclinnan.weather.domain;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CityDO {
	private String name;
	private BigDecimal lat;
	private BigDecimal lon;
	private String country;
	private String state;
}
