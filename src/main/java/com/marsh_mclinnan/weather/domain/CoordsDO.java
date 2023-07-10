package com.marsh_mclinnan.weather.domain;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CoordsDO {

	private BigDecimal lat;
	private BigDecimal lon;
}
