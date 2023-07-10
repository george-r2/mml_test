package com.marsh_mclinnan.weather.domain;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CityDO {
	private String name;
	private BigDecimal lat;
	private BigDecimal lon;
	private String country;
	private String state;
}
